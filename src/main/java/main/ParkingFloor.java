package main;

import java.util.*;

// assume Vehicle and ParkingSpot are unchanged
public class ParkingFloor {
    private final int level;
    private final List<ParkingSpot> spots;                       // keep master list of all spots
    private final Map<String, Deque<ParkingSpot>> freeSpotsBySize;
    private final int totalSpots;

    public ParkingFloor(int level, List<ParkingSpot> spots) {
        this.level = level;
        this.spots = new ArrayList<>(spots);
        this.totalSpots = spots.size();

        // initialize the map of size → free‐spot‐deque
        this.freeSpotsBySize = new HashMap<>();
        for (ParkingSpot spot : this.spots) {
            Deque<ParkingSpot> spotsize = freeSpotsBySize.get(spot.getSize());
            if (!spot.isOccupied()) {
                if(spotsize == null) {
                    spotsize = new ArrayDeque<>();
                    freeSpotsBySize.put(spot.getSize(), spotsize);
                }
                freeSpotsBySize.get(spot.getSize()).addLast(spot);

            }
        }
    }

    /**
     * O(1): grab one free spot of exactly this size, or return null
     */
    public ParkingSpot findAvailableSpot(Vehicle vehicle) {
        Deque<ParkingSpot> freeList = freeSpotsBySize.get(vehicle.getSize());
        if (freeList == null || freeList.isEmpty()) {
            return null;
        }
        ParkingSpot spot = freeList.pollFirst();  // O(1)
        spot.assignVehicle(vehicle);
        return spot;
    }

    /**
     * O(1): mark spot free again
     */
    public void releaseSpot(ParkingSpot spot) {
        spot.removeVehicle();
        freeSpotsBySize
                .computeIfAbsent(spot.getSize(), s -> new ArrayDeque<>())
                .addLast(spot);  // O(1)
    }

    public int getTotalSpots() {
        return totalSpots;
    }

    /**
     * O(k) where k = number of distinct sizes; usually tiny
     */
    public int getAvailableSpots() {
        int sum = 0;
        for (Deque<ParkingSpot> dq : freeSpotsBySize.values()) {
            sum += dq.size();     // size() of ArrayDeque is O(1)
        }
        return sum;
    }

    /**
     * O(1) count for a particular size
     */
    public int getAvailableSpots(String size) {
        Deque<ParkingSpot> dq = freeSpotsBySize.get(size);
        return (dq == null) ? 0 : dq.size();
    }

    /**
     * Return a snapshot of free spots grouped by size
     */
    public Map<String, List<ParkingSpot>> getFreeSpotsBySize() {
        Map<String, List<ParkingSpot>> result = new HashMap<>();
        for (Map.Entry<String, Deque<ParkingSpot>> entry : freeSpotsBySize.entrySet()) {
            result.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return result;
    }

    /**
     * Convenience to list the sizes you support
     */
    public Set<String> getAllSizes() {
        return freeSpotsBySize.keySet();
    }

    /**
     * Pull out one size’s free‐list as a snapshot
     */
    public List<ParkingSpot> getFreeSpotsOfSize(String size) {
        Deque<ParkingSpot> dq = freeSpotsBySize.get(size);
        return (dq == null) ? Collections.emptyList() : new ArrayList<>(dq);
    }
}
