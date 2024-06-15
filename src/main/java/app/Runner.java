package app;

import config.SessionCreator;
import entity.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Runner {
    public static void main(String[] args) {
        SessionCreator sessionCreator = new SessionCreator();
        try (sessionCreator) {

            createNewCustomer(sessionCreator);
            customerReturnsFilm(sessionCreator);
            customerRentedFreeFilm(sessionCreator);
            createNewFilmWithInventory(sessionCreator);

        }
    }

    private static void createNewCustomer(SessionCreator sessionCreator) {
        Session session = sessionCreator.getSession();
        try (session) {
            session.beginTransaction();
            City city = session.find(City.class, 343L);

            Address address = Address.builder()
                    .city(city)
                    .address("Some Address")
                    .district("Moscow Street")
                    .postalCode("000001")
                    .phone("1234567890")
                    .build();

            session.persist(address);

            Store store = session.find(Store.class, 1L);

            Customer customer = Customer.builder()
                    .firstName("Vasya")
                    .lastName("Pupkin")
                    .email("vasyapupkin@email.ru")
                    .address(address)
                    .store(store)
                    .isActive(true)
                    .build();
            session.persist(customer);
            session.getTransaction().commit();

            System.out.println("===>>> Создание нового покупателя (таблица customer) со всеми зависимыми полями <<<===");
            System.out.println("Customer создан: " + customer);
            System.out.println("Address для Customer создан: " + address);

        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }

    private static void customerReturnsFilm(SessionCreator sessionCreator) {
        Session session = sessionCreator.getSession();
        try (session) {
            session.beginTransaction();
            Query<Rental> rentalQuery = session.createQuery(
                    "SELECT r FROM Rental r " +
                            "WHERE r.returnDate IS NULL", Rental.class);
            rentalQuery.setMaxResults(1);
            Rental rental = rentalQuery.getSingleResult();
            Customer customer = rental.getCustomer();
            rental.setReturnDate(new Date());

            session.getTransaction().commit();

            System.out.println("===>>> Событие «покупатель пошел и вернул ранее арендованный фильм» <<<===");
            System.out.println("Завершенная аренда: " + rental);
            System.out.println("Покупатель, который вернул фильм: " + customer);

        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }

    private static void customerRentedFreeFilm(SessionCreator sessionCreator) {
        Session session = sessionCreator.getSession();
        try (session) {
            session.beginTransaction();
            Customer customer = session.get(Customer.class, 1L);
            Store store = customer.getStore();
            Staff managerStoreStaff = store.getManagerStaff();

            Query<Inventory> inventoryQuery = session.createQuery(
                    "SELECT i FROM Inventory i " +
                            "WHERE i.inventoryId NOT IN " +
                            "(SELECT r.inventory.inventoryId FROM Rental r " +
                            "WHERE r.returnDate IS NULL)", Inventory.class);

            Inventory inventory = inventoryQuery.list().getFirst();

            Film rentFilm = inventory.getFilm();

            Rental rental = Rental.builder()
                    .rentalDate(new Date())
                    .staff(managerStoreStaff)
                    .customer(customer)
                    .inventory(inventory)
                    .build();

            session.persist(rental);

            Payment payment = Payment.builder()
                    .paymentDate(new Date())
                    .customer(customer)
                    .amount(0.99D)
                    .rental(rental)
                    .staff(managerStoreStaff)
                    .build();

            session.persist(payment);
            session.getTransaction().commit();

            System.out.println("===>>> Событие «покупатель сходил в магазин (store) и арендовал (rental) там инвентарь (inventory). При этом он сделал оплату (payment) у продавца (staff)» <<<===");
            System.out.println("Прокатный фильм: " + rentFilm);
            System.out.println("Инвентарь фильма: " + inventory);
            System.out.println("Магазин: " + store);
            System.out.println("Продавец магазина: " + managerStoreStaff);
            System.out.println("Клиент магазина: " + customer);
            System.out.println("Аренда фильма: " + rental);
            System.out.println("Оплата: " + payment);
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }

    private static void createNewFilmWithInventory(SessionCreator sessionCreator) {
        Session session = sessionCreator.getSession();
        try (session) {
            session.beginTransaction();

            Language originalLanguage = Language.builder()
                    .name("Russian")
                    .build();
            session.persist(originalLanguage);

            Language language = session.get(Language.class, 1L);
            Query<Category> categoryQuery = session.createQuery(
                    "SELECT c FROM Category c " +
                            "WHERE c.categoryId IN (5, 6, 10)", Category.class);
            List<Category> categories = categoryQuery.list();

            Query<Actor> actorQuery = session.createQuery(
                    "SELECT a FROM Actor a " +
                            "WHERE a.actorId BETWEEN 40 AND 50", Actor.class);
            List<Actor> actors = actorQuery.list();

            Film film = Film.builder()
                    .title("Приключения Java-разработчика")
                    .description("Культовый боевик с элементами головоломки")
                    .releaseYear(Year.now())
                    .originalLanguage(originalLanguage)
                    .language(language)
                    .rentalDuration(6L)
                    .rentalRate(9.99D)
                    .length(189L)
                    .replacementCost(99.99D)
                    .rating("NC-17")
                    .specialFeatures("Trailers")
                    .filmCategories(categories)
                    .filmActors(actors)
                    .build();

            session.persist(film);

            Query<Store> storeQuery = session.createQuery(
                    "SELECT s FROM Store s", Store.class);

            List<Store> stores = storeQuery.list();

            Collection<Inventory> inventories = new ArrayList<>();

            for (Store store : stores) {
                for (int i = 0; i < 5; i++) {
                    Inventory inventory = Inventory.builder()
                            .store(store)
                            .film(film)
                            .build();
                    inventories.add(inventory);
                }
            }
            inventories.forEach(session::persist);

            session.getTransaction().commit();

            System.out.println("===>>> Событие «сняли новый фильм, и он стал доступен для аренды» <<<===");
            System.out.println("Новый фильм: " + film);
            System.out.println("Доступные для аренды копии: ");
            for (Inventory inventory : inventories) {
                System.out.println("Копия фильма " + inventory);
            }


        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }

}
