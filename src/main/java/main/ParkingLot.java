package main;

import java.util.*;

// assume Vehicle, ParkingSpot, and the optimized ParkingFloor are unchanged

public class ParkingLot {
    private static ParkingLot instance;

    // Hierarchy & lookup
    private final List<ParkingFloor> floors = new ArrayList<>();
    private final Map<ParkingSpot, ParkingFloor> spotFloorMap = new HashMap<>();

    // Global O(1) free‑spot buckets
    private final Map<String, Deque<ParkingSpot>> freeSpotsBySize = new HashMap<>();

    private int totalSpots = 0;
    private int availableSpots = 0;

    private ParkingLot() { }

    public static synchronized ParkingLot getInstance() {
        if (instance == null) {
            instance = new ParkingLot();
        }
        return instance;
    }

    /**
     * Add a floor: registers each spot → floor, and seeds global free‑spot queues.
     * O(n) in floor’s spot count, only on startup or when adding floors.
     */
    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
        totalSpots += floor.getTotalSpots();
        availableSpots += floor.getAvailableSpots();

        // Register every spot (so we can find its floor later)
        for (String size : floor.getAllSizes()) {
            List<ParkingSpot> freeOnThatSize = floor.getFreeSpotsOfSize(size);
            // map each spot back to this floor
            for (ParkingSpot spot : freeOnThatSize) {
                spotFloorMap.put(spot, floor);
            }
            // seed global queue
            freeSpotsBySize
                    .computeIfAbsent(size, s -> new ArrayDeque<>())
                    .addAll(freeOnThatSize);
        }
    }

    /**
     * O(1): pops a free spot from the global queue, marks it occupied,
     * decrements globals and returns it.
     */
    public ParkingSpot findAvailableSpot(Vehicle vehicle) {
        Deque<ParkingSpot> dq = freeSpotsBySize.get(vehicle.getSize());
        if (dq == null || dq.isEmpty()) {
            return null;
        }

        ParkingSpot spot = dq.pollFirst();  // O(1)
        spot.assignVehicle(vehicle);
        availableSpots--;

        // Also remove it from its floor’s own free‑queue:
        ParkingFloor floor = spotFloorMap.get(spot);
        if (floor != null) {
            floor.releaseSpot(spot);       // floor will re‑add it as free, so we need a floor‐specific “park” method
            // You may want to add a floor.parkSpot(spot) that just assigns without adding back
        }

        return spot;
    }

    /**
     * O(1): frees the spot in both global and its floor.
     */
    public void releaseSpot(ParkingSpot spot) {
        spot.removeVehicle();
        availableSpots++;

        // 1) put back into the floor’s free‑queues
        ParkingFloor floor = spotFloorMap.get(spot);
        if (floor != null) {
            floor.releaseSpot(spot);
        }

        // 2) put back into global free‑queues
        freeSpotsBySize
                .computeIfAbsent(spot.getSize(), s -> new ArrayDeque<>())
                .addLast(spot);
    }

    public int getTotalSpots() {
        return totalSpots;
    }

    public int getAvailableSpots() {
        return availableSpots;
    }

    /** If you ever need to inspect floors directly: */
    public List<ParkingFloor> getFloors() {
        return Collections.unmodifiableList(floors);
    }
}
