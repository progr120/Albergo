import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Albergo {
    public static void main(String[] args) throws Exception {
        loadRoomsFromCSV();
        Menu();
        mainMenu();
    }

    static List<Room> listRooms = new ArrayList<>();
    static Scanner s = new Scanner(System.in);

    static void mainMenu() throws Exception { // menu albergo, con le opzioni di funzione
        while (true) {
            System.out.println("╔════════════════════════════╗");
            System.out.println("║        MENU ALBERGO        ║");
            System.out.println("╠════════════════════════════╣");
            System.out.println("║ [I] checkin  [O] checkout  ║");
            System.out.println("╠════════════════════════════╣");
            System.out.println("║ [L] lista    [C] crearoom  ║");
            System.out.println("╠════════════════════════════╣");
            System.out.println("║ [R] report   [E] esci      ║");
            System.out.println("╚════════════════════════════╝");

            System.out.println("\nInserisci una opzione del menu: ");
            String v = s.nextLine().toLowerCase();
            switch (v) { // crea le opzioni dal menu
                case "i":
                    checkIN();
                    break;
                case "o":
                    checkOUT();
                    break;
                case "l":
                    infoRooms();
                    break;
                case "c":
                    creaRooms();
                    break;
                case "r":
                    albergoReport();
                    break;
                case "e":
                    System.out.println("\nGrazie per la visita, buona giornata!!!");
                    return;
                default:
                    System.out.println("Opzione non valida, per favore riprova.");
                    break;
            }
        }
    }

    static void creaRooms() {
        // creaRoom
        // chiede quanti stanze vuoi creare
        System.out.println("Quante stanze vuoi creare?");
        int numRooms = Integer.parseInt(s.nextLine());

        // secondo info di quanti stanze vuoi creare
        // un metodo for che ripete finche i arriva al mio numero di
        // "quante staze vuoi creare?"
        for (int i = 0; i < numRooms; i++) {
            createRoomFromUserInput();
        }
    }

    static void createRoomFromUserInput() {
        // chiede info dei rooms che crea
        // lascia un massagio che ha creato una room
        System.out.println("Inserisci i dettagli della stanza: ");

        System.out.print("Numero della stanza: ");
        String roomNumber = s.nextLine();

        System.out.print("Capacità: ");
        int capacity = Integer.parseInt(s.nextLine());

        System.out.print("Descrizione: ");
        String description = s.nextLine();

        Room newRoom = new Room(roomNumber, capacity, description);
        listRooms.add(newRoom);

        System.out.println("Stanza creata.");
    }

    static void loadRoomsFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("rooms.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String roomNumber = parts[0];
                int capacity = Integer.parseInt(parts[1]);
                String description = parts[2];
                Room newRoom = new Room(roomNumber, capacity, description);
                listRooms.add(newRoom);
            }
        } catch (IOException e) {
            System.out.println("Errore durante la lettura del file CSV: " + e.getMessage());
        }
    }

    static void writeRoomsToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("rooms.csv"))) {
            for (Room room : listRooms) {
                bw.write(room.getRoomNumber() + "," + room.getCapacity() + "," + room.getDescription());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Errore durante la scrittura del file CSV: " + e.getMessage());
        }
    }

    static void Menu() throws Exception {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            writeRoomsToCSV();
            System.out.println("Stanze salvate nel file CSV. Arrivederci!");
        }));
    }

    static void infoRooms() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║         WELCOME INFOROOMS        ║");
        System.out.println("╚══════════════════════════════════╝");
        boolean continua = true;
        while (continua) {
            for (Room room : listRooms) {
                System.out.println("Numero stanza: " + room.getRoomNumber());
                System.out.println("Capacità stanza: " + room.getCapacity());
                System.out.println("Numero ospiti: " + room.getNumberGuests());
                System.out.println("Descrizione: " + room.getDescription());
                System.out.println("Stato: " + (room.isFree() ? "Libera" : "Occupata"));
                System.out.println("------------------------------------");

                // Aggiungi il controllo per indicare se la stanza è prenotata
                if (!room.isFree()) {
                    System.out.println("Clienti nella stanza (" + room.getRoomNumber() + ")");
                    for (Guest client : room.getGuests()) {
                        System.out.println("Nome: " + client.getFirstName());
                        System.out.println("Cognome: " + client.getLastName());
                        // Aggiungi altre informazioni del cliente se necessario
                    }
                    System.out.println("------------------------------------");
                }
            }
            System.out.println("Apposto? Se sì, apre menu (s/n)");
            String risposta = s.nextLine().toLowerCase();
            if (!risposta.equals("s")) {
                continua = true;
            } else {
                risposta.equals("n");
                continua = false;
            }
        }
    }

    static int getnumberFreeRooms() {
        int freeRoom = 0;
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

    static void infoGuestsInRoom(String roomNumber) {
        boolean roomFound = false;
        for (Room room : listRooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                roomFound = true;
                ArrayList<Guest> guests = room.getGuests();
                if (guests.isEmpty()) {
                    System.out.println("Non ci sono ospiti nella stanza.");
                } else {
                    System.out.println("Ospiti nella stanza " + roomNumber + ":");
                    for (Guest guest : guests) {
                        System.out.println("Nome: " + guest.getFirstName() + " Cognome: " + guest.getLastName());
                    }
                }
                break;
            }
        }
        if (!roomFound) {
            System.out.println("Stanza non trovata.");
        }
    }

    static void checkOUT() {
        System.out.print("\nInserire numero camera per il checkout: ");
        String roomNumber = s.nextLine();

        // Mostra gli ospiti nella stanza
        infoGuestsInRoom(roomNumber);

        boolean roomFound = false;
        for (Room room : listRooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                roomFound = true;
                if (room.getGuests().isEmpty()) {
                    System.out.println("La stanza è già vuota.");
                } else {
                    System.out.println("\nConfermi il checkout per la stanza " + roomNumber + "? (s/n)");
                    String confirm = s.nextLine().toLowerCase();
                    if (confirm.equals("s")) {
                        System.out.println("Benvenuto al check-out della camera " + roomNumber);
                        System.out.println("Inserire il numero di ospiti che lasciano la stanza: ");
                        int numGuestsToCheckout = s.nextInt();
                        s.nextLine();

                        for (int i = 0; i < numGuestsToCheckout; i++) {
                            System.out.println("Inserire il cognome dell'ospite " + (i + 1) + ":");
                            String guestSurname = s.nextLine();
                            System.out.println("Inserire il nome dell'ospite " + (i + 1) + ":");
                            String guestName = s.nextLine();

                            // Rimuovi l'ospite dalla stanza
                            room.removeGuest(guestName, guestSurname);
                        }
                        System.out.println("Checkout completato per la camera " + roomNumber);
                    } else {
                        System.out.println("Checkout annullato.");
                    }
                }
                break;
            }
        }
        if (!roomFound) {
            System.out.println("Stanza non trovata.");
        }
    }

    static void checkIN() {
        System.out.println("\nInserire il numero di ospiti per il check-in: ");
        int numGuests = s.nextInt();
        s.nextLine();

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
            System.out.println("Siamo pieni.");
            return;
        } else {
            System.out.println("Camera libera numero: " + listRooms.get(chiave).getRoomNumber());
        }

        for (int i = 0; i < numGuests; i++) {
            Guest newguest = creaGuest();
            listRooms.get(chiave).addGuest(newguest);
        }
    }

    private static Guest creaGuest() {
        System.out.print("Inserisci cognome: ");
        String cognome = s.nextLine();
        System.out.print("Inserisci nome: ");
        String nome = s.nextLine();
        Guest newg = new Guest(nome, cognome);
        return newg;
    }

    static void albergoReport() {
        int occupiedRooms = 0;
        int freeRooms = 0;
        int totalGuests = 0;

        for (Room room : listRooms) {
            if (room.isFree()) {
                freeRooms++;
            } else {
                occupiedRooms++;
                totalGuests += room.getNumberGuests();
            }
        }
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║             ALBERGO REPORT            ║");
        System.out.println("╚═══════════════════════════════════════╝");
        boolean continua = true;
        while (continua) {
            System.out.println("Stanze occupate: " + occupiedRooms);
            System.out.println("Stanze libere: " + freeRooms);
            System.out.println("Totale ospiti: " + totalGuests);
            // creato questo perche cosi il menu non scompare con info
            System.out.println("Apposto? se sì, apre menu (s/n)");
            String risposta = s.nextLine().toLowerCase();
            if (!risposta.equals("s")) {
                continua = true;
            } else {
                risposta.equals("n");
                continua = false;
            }
        }
    }
}