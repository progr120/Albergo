package Albergo_copy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Albergo {
    static ArrayList<Room> listRooms = new ArrayList<Room>();

    static void createRooms() {

        /*
         * 1 apro il file delle stanze
         * 2 leggo la prima riga a vuoto
         * 3 leggo la seconda con i dati prima stanza
         * 4 cerco di capire quali sono i dati separati da ;
         * 5 il primo e il numr il 2 la capicita il terzo la descrizione il 4 il prezzo
         * 6 li metto nelle 3 variabili
         * 7 creo oggetto con le 3 variabili
         * 8 lo agguingo alla lista rooms
         * 9 passo ad un 'altra riga e ricominl
         * cio da 3
         * 
         */
        try {
            File myObj = new File("Rooms.csv"); // apro il file delle stanze
            Scanner myReader = new Scanner(myObj); // lettore
            myReader.nextLine(); // prima riga a vuoto
            // ciclo per seconda terza etc righe
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine(); // data= "312;6;camera con vista"
                String[] items = data.split(";"); // uso ";" per separa i dati in array di string
                Room newRoom;
                String nr = items[0]; // primo elemento 0 il room number
                int cap = Integer.parseInt(items[1]); // 2 elemento 1 la capacity !!! string convertire in intero
                String desc = items[2];
                // attenzione al tipo o roomopen o room standard
                String tipo = items[4]; // o std oppure open
                if (tipo.equals("open")) {
                    newRoom = new RoomOpen(nr, cap, desc);// creare la room
                } else {
                    newRoom = new Room(nr, cap, desc);// creare la room
                }
                // devo scorrere guests.csv e trovare ad esempio i "120"
                // nel caso crear un guest e add alla lista
                File fileg = new File("Guests.csv");
                Scanner scg = new Scanner(fileg);
                scg.nextLine(); // salto intestazione
                while (scg.hasNextLine()) {
                    String datag = scg.nextLine(); // data= "312;guerrini;luca"
                    String[] itemsg = datag.split(";"); // uso ";" per separa i dati in array di string
                    if (itemsg[0].equals(nr)) {
                        // creo guest
                        Guest newg = new Guest(itemsg[1].trim(), itemsg[2].trim());
                        newRoom.addGuest(newg);
                    }
                }
                scg.close();
                System.out.println(newRoom.getClass().getName());

                listRooms.add(newRoom); // aggiungerla alla lista stanze

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    static void menuAlbergo() {
        while (true) {
            System.out.println("\n---MENU ALBERGO ---");
            System.out.println("[I] checkin - [O] checkout - [L] lista stanze - [R] report albergo - [E] esci");
            System.out.print("ins voce : ");
            Scanner sc = new Scanner(System.in);
            String v = sc.nextLine();
            v = v.trim().toLowerCase();
            if (v.equals("i"))
                checkIN();
            if (v.equals("o"))
                checkOUT();
            if (v.equals("l"))
                infoRooms();
            if (v.equals("r"))
                System.out.println(printReport());
            if (v.equals("e"))
                break;
        }
    }

    private static String printReport() {
        System.out.println("\n--- REPORT ALBERGO ---");
        String report = "";
        String riga = "";
        // scorro tutte le stanze per recuperare informazioni varie
        // numero guests , libere, occupate, capacita' totale
        int totguests = 0;
        int totfree = 0;
        int totfull = 0;
        int totcapacity = 0;
        String listafree = "";
        for (Room r : listRooms) {
            totguests += r.getNumberGuests();
            if (r.isFree()) {
                totfree++;
                listafree += "(" + r.getCapacity() + ")" + r.getRoomNumber() + " ";
            }
            totcapacity += r.getCapacity();
        }
        totfull = listRooms.size() - totfree;
        // Stanze libere: 2 - occupate 1
        riga = "Stanze libere: " + totfree + " - occupate: " + totfull;
        report += riga + "\n";
        // Ospiti totali: 3
        riga = "Ospiti totali: " + totguests + " - tot capacita': " + totcapacity;
        report += riga + "\n";
        float perc = (float) totguests / (float) totcapacity;
        riga = "Occupazione in %: " + Math.round(perc * 100) + "%";
        report += riga + "\n";
        // lista numeri stanze libere: 312, 334
        riga = "Lista stanze libere: " + listafree;
        report += riga + "\n";
        return report;
    }

    public static void main(String[] args) throws Exception {
        createRooms();
        menuAlbergo();

    }

    private static void infoRooms() {
        System.out.println("--- WELCOME INFOROOMS ---");
        for (Room r : listRooms) {
            System.out.println(r.getInfoRoom());
        }
    }

    /**
     * funzione per avere il numero di stanze libere
     * 
     * @return int numero stanze libere
     */
    static int getnumberFreeRooms() {
        int freeRoom = 0;
        // vado davanti a ogni stanza e se libera la conto
        // lista stanze in arraylist di diemnsione size()
        for (int i = 0; i < listRooms.size(); i++) {
            if (listRooms.get(i).isFree()) {
                freeRoom++;
            }
        }
        return freeRoom;
    }

    static int getTotGuests() {
        int tot = 0;
        for (Room room : listRooms) {
            tot += room.getNumberGuests();
        }
        return tot;
    }

    static void checkOUT() {
        // salutare welcome checkout
        // chiedere numero chiave
        // eliminare ospiti da listaguest room[chiave]
        // eventuali messaggi di stanza gia' vuota
        System.out.println("\n--- WELCOME to CHECKOUT ---");
        Scanner sc = new Scanner(System.in);
        System.out.println("\nDammi il numero della chiave");
        String chiave = sc.nextLine();
        // chiedo la stanza e recupero l'indice in arraylist
        int indexRoom = getIndexByRoomNumber(chiave);
        if (indexRoom == -1) { // se non torvata saluto ed esco
            System.out.println(chiave + " !!! Camera inesistente");
            return;
        }
        // listRooms.get(indexRoom).freeGuests();
        Room room = listRooms.get(indexRoom); // metto le mani sulla stanza da liberare
        room.freeGuests(); // libero la stanza non uso l'info di quanti ne ha tolti
        System.out.println("--- ROOM " + chiave + " LIBERA ---");
        salvaGuests();
    }

    static void checkIN() {
        /*
         * fase1 quanti siete
         * fase 2 cerco stanza ok
         * fase 3 prendo la chiave
         * prendo documenti e addguest per tutti nella stanza
         */
        Scanner sc = new Scanner(System.in);
        System.out.println("\ninserire numero ospiti checkin");
        int numGuests = sc.nextInt();
        int chiave = -1;
        for (int i = 0; i < listRooms.size(); i++) {
            Room r = listRooms.get(i);
            boolean disp = r.isAvailableFor(numGuests);
            if (disp) {
                chiave = i;
                break;
            }
        }
        if (chiave == -1) {
            System.out.println("siamo pieni");
            return;
        } else {
            System.out.println("camera libera numero: " + listRooms.get(chiave).getRoomNumber());
        }
        // ciclo per tutti dove creo il guest e lo assegno alla stanza
        for (int i = 0; i < numGuests; i++) {
            Guest newguest = creaGuest();
            listRooms.get(chiave).addGuest(newguest);
        }
        salvaGuests();
    }

    private static void salvaGuests() {
        String txfile = "roomnumber;cognome;nome\n";
        // saper scorrer le stanze
        for (Room r : listRooms) {
            // se nessuno vado al prossimo con continue
            if (r.isFree())
                continue;
            // saper socrrere tu i gustests
            txfile += r.getCSVguests();
        }
        try {
            FileWriter file = new FileWriter("Guests.csv");
            file.write(txfile);
            file.close();
        } catch (Exception e) {
            System.out.println("data not saved!!");
        }

    }

    private static Guest creaGuest() {
        Scanner sc = new Scanner(System.in);
        System.out.print("ins cognome: ");
        String cognome = sc.nextLine();
        System.out.print("ins nome: ");
        String nome = sc.nextLine();
        Guest newg = new Guest(nome, cognome);
        return newg;
    }

    static int getIndexByRoomNumber(String roomNumber) {
        int index = -1;
        roomNumber = roomNumber.trim().toUpperCase();
        for (int i = 0; i < listRooms.size(); i++) {
            // Room r =listRooms.get(i);
            if (listRooms.get(i).getRoomNumber().equalsIgnoreCase(roomNumber)) {
                index = i;
            }
        }
        return index;
    }

}