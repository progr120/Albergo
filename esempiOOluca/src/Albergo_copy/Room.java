package Albergo_copy;

import java.util.ArrayList;

public class Room {
    /**
     * Classe stanza albergo
     */
    private String roomNumber;
    private int capacity;
    private String description;
    protected ArrayList<Guest> listGuests;

    public Room(String roomNumber, int capacity, String description) {
        this.roomNumber = roomNumber.trim().toUpperCase();
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

    public int freeGuests() {
        int numfree = listGuests.size();
        listGuests.clear();
        return numfree;
    }

    public String getInfoRoom() {
        String ris = "";
        // "Status [3] - numero: 312 - max persone: 3 - descrizione: terrazza mare con idro..."
        String status ="";
        if (isFree()){
            status+= "-";
        } 
        else {
            status+=getNumberGuests();
        }
        //verifico la classe e preparo testo per tipologia
        String tipo=this.getClass().getSimpleName();
        if (tipo.equals("RoomOpen")){
            tipo="OPEN";
        }
        else{
            tipo="STD";
        }
        ris += tipo +"[" + status + "] - ";
        
        ris += "Numero: " + roomNumber;
        ris += " - max persone: " + capacity;
        // se description lungo lo taglio max 40 caratteri
        String mydescr=description;
        int maxl=40;
        // se troppo lungo lo taglio
        if (mydescr.length()>maxl){
            mydescr=mydescr.substring(0, maxl-3) + "...";
        }
        
        ris += " - descrizione: " + mydescr;
        if (!isFree()) { // aggiungo guest a ris
            int conta=1;
            for (Guest g:listGuests){ // scorro tutt i guests
                String rigaGuest= conta + ") " + g.getInfoGuest();
                ris += "\n" + rigaGuest;
                conta++;
            }
        }
        return ris + "\n";
    }

    public String getCSVguests() {
        /*
        esempio "120;qwe;qwe\n120;wer;wer\n"
        */
        String ris="";
        for (Guest g:listGuests){
            ris+= roomNumber + ";" + g.getLastName() + ";" + g.getFirstName()+"\n";
        }
        return ris;    
    }
}