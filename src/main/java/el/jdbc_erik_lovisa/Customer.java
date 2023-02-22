package el.jdbc_erik_lovisa;




public record Customer(int id, String firstName, String lastName, String country, String postalCode, String phoneNr, String email) {

    public Customer(String firstName, String lastName, String country, String postalCode, String phoneNr, String email) {
        this(-1, firstName, lastName, country, postalCode, phoneNr, email);
    }

}