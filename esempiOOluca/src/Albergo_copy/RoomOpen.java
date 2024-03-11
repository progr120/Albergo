package Albergo_copy;

public class RoomOpen extends Room  {

    public RoomOpen(String roomNumber, int capacity, String description) {
        super(roomNumber, capacity, description);
        //TODO Auto-generated constructor stub
    }
    /**
     * verifica la capacita' per un determinato numero di persone
     * 
     * @param num intero che definisce quante persone devono essere ospiti
     * @return boolean vero falso se disponibile per num persone
     */
    public boolean isAvailableFor(int num) {
        boolean ris = false;
        int available=getCapacity()-getNumberGuests();
        if ( num <= available) {
            ris = true;
        }
        return ris;
    }

    
}