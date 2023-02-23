package el.jdbc_erik_lovisa.models;


/**
 *
 * @param id
 * @param firstName
 * @param lastName
 * @param country
 * @param postalCode
 * @param phoneNr
 * @param email
 */
public record Customer(int id, String firstName, String lastName, String country, String postalCode, String phoneNr, String email) {

    /**
     * This constructor is used when creating a new customer and the id is not yet known.
     * @param firstName
     * @param lastName
     * @param country
     * @param postalCode
     * @param phoneNr
     * @param email
     */
    public Customer(String firstName, String lastName, String country, String postalCode, String phoneNr, String email) {
        this(-1, firstName, lastName, country, postalCode, phoneNr, email);
    }

}