package Albergo_personale;
import java.util.ArrayList;

public class Room {
    /**
     * Classe stanza albergo
     */
    private String roomNumber;
    private int capacity;
    private String description;
    private ArrayList<Guest> listGuests;

    public Room(String roomNumber, int capacity, String description) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.description = description;
        listGuests = new ArrayList<Guest>();
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * aggiunge alla stanza un guest
     * 
     * @param newGuest nuovo oggetto guest da aggiungere
     * @return true o false
     */
    public boolean addGuest(Guest newGuest) {
        boolean ris = false;
        if (listGuests.size() < capacity) { // c'e' posto
            // aggiungo guest
            listGuests.add(newGuest);
            ris = true;
        }
        return ris;
    }

    /**
     * fornisce numero guests della stanza
     * 
     * @return numero ospiti
     */
    public int getNumberGuests() {
        int ris = 0;
        ris = listGuests.size();
        return ris;
    }

    /**
     * metodo per sapere se stanza libera o occupata
     * 
     * @return boolean ritorna true se libera e false se occupata
     */
    public boolean isFree() {
        boolean ris = false;
        if (listGuests.size() == 0) {
            ris = true;
        }
        return ris;
    }

    /**
     * verifica la capacita' per un determinato numero di persone
     * 
     * @param num intero che definisce quante persone devono essere ospiti
     * @return boolean vero falso se disponibile per num persone
     */
    public boolean isAvailableFor(int num) {
        boolean ris = false;
        if (isFree() && num <= capacity) {
            ris = true;
        }
        return ris;
    }

    /**
     * Restituisce la lista degli ospiti nella stanza
     * 
     * @return lista degli ospiti
     */
    public ArrayList<Guest> getGuests() {
        return listGuests;
    }

    /**
     * Rimuove un ospite dalla stanza dato il suo nome e cognome
     * 
     * @param guestName    nome dell'ospite
     * @param guestSurname cognome dell'ospite
     * @return true se l'ospite è stato rimosso con successo, false altrimenti
     */
    public boolean removeGuest(String guestName, String guestSurname) {
        for (Guest guest : listGuests) {
            if (guest.getFirstName().equals(guestName) && guest.getLastName().equals(guestSurname)) {
                listGuests.remove(guest);
                return true; // Ospite rimosso con successo
            }
        }
        return false; // Ospite non trovato
    }
}
