import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BusRouteTracker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Enter Data (Veri Girişi)
        List<String> stopNames = new ArrayList<>();
        List<Integer> passengersBoarding = new ArrayList<>();
        List<Integer> passengersAlighting = new ArrayList<>();

        System.out.print("Kac adet durak var? / How many stops are on the route?: ");
        int numStops = scanner.nextInt();
        scanner.nextLine(); // Scanner satır atlama temizliği

        for (int i = 0; i < numStops; i++) {
            System.out.println("\n--- " + (i + 1) + ". Durak / Stop ---");
            System.out.print("Durak adi / Stop name: ");
            String name = scanner.nextLine();

            System.out.print("Binen yolcu sayisi / Passengers boarding: ");
            int boarding = scanner.nextInt();

            System.out.print("inen yolcu sayisi / Passengers alighting: ");
            int alighting = scanner.nextInt();
            scanner.nextLine();

            stopNames.add(name);
            passengersBoarding.add(boarding);
            passengersAlighting.add(alighting);
        }

        System.out.print("\nOtobusun koltuk kapasitesi / Bus's seating capacity: ");
        int seatingCapacity = scanner.nextInt();

        // Hesaplamalar için değişkenler ve listeler
        List<Integer> currentOccupancies = new ArrayList<>();
        int currentOccupancy = 0;
        int overCapacityStopsCount = 0;

        System.out.println("\n==================================================");
        System.out.println("YOLCULUK İŞLENİYOR / PROCESSING ROUTE...");
        System.out.println("==================================================");

        // 2. Occupancy Calculation & 3. Capacity Check
        for (int i = 0; i < numStops; i++) {
            String name = stopNames.get(i);
            int boarding = passengersBoarding.get(i);
            int alighting = passengersAlighting.get(i);

            // İnen kişi sayısı mevcut yolcu sayısından fazlaysa hata kontrolü
            if (currentOccupancy + boarding - alighting < 0) {
                System.out.println("Data error at [" + name + "]: cannot have more passengers alighting than are currently on the bus. Occupancy set to 0.");
                currentOccupancy = 0;
            } else {
                currentOccupancy = currentOccupancy + boarding - alighting;
            }

            currentOccupancies.add(currentOccupancy);
            System.out.println("Durak: " + name + " -> Otobüsteki Anlık Yolcu Sayısı: " + currentOccupancy);

            // Kapasite kontrolü (Capacity Check)
            if (currentOccupancy > seatingCapacity) {
                System.out.println("Warning: Bus is over capacity at [" + name + "]!");
                overCapacityStopsCount++;
            }
        }

        // 1. Display All (Tüm Durakların Listelenmesi)
        System.out.println("\n==================================================");
        System.out.println("TÜM DURAKLAR / DISPLAY ALL STOPS");
        System.out.println("==================================================");
        for (int i = 0; i < numStops; i++) {
            System.out.println("Durak Adi: " + stopNames.get(i) +
                    " | Binen: " + passengersBoarding.get(i) +
                    " | İnen: " + passengersAlighting.get(i) +
                    " | Anlık Yolcu (Occupancy): " + currentOccupancies.get(i));
        }

        // 2. Statistics (İstatistikler)
        System.out.println("\n==================================================");
        System.out.println("İSTATİSTİKLER / STATISTICS");
        System.out.println("==================================================");

        // En kalabalık durağı bulma (En çok binen yolcuya göre)
        int maxBoarding = Collections.max(passengersBoarding);
        int busiestStopIndex = passengersBoarding.indexOf(maxBoarding);
        String busiestStop = stopNames.get(busiestStopIndex);
        System.out.println("En Kalabalık Durak (Biniş) / Busiest Stop: " + busiestStop + " (" + maxBoarding + " binen yolcu)");

        // Ortalama yolcu yoğunluğu
        double totalOccupancy = 0;
        for (int occ : currentOccupancies) {
            totalOccupancy += occ;
        }
        double avgOccupancy = totalOccupancy / numStops;
        System.out.printf("Ortalama Yolcu Yoğunluğu / Average Occupancy: %.2f\n", avgOccupancy);

        // Kapasitenin aşıldığı durak sayısı
        System.out.println("Kapasitenin Aşıldığı Durak Sayısı / Over Capacity Stop Count: " + overCapacityStopsCount);

        // Son durak kontrolü (Final Occupancy Check)
        int finalOccupancy = currentOccupancies.get(numStops - 1);
        if (finalOccupancy != 0) {
            System.out.println("Warning: " + finalOccupancy + " passengers still on the bus after the final stop — please check your data.");
        }

        scanner.close();
    }
}
