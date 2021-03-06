package by.epam.buhai.airline.entity;

import by.epam.buhai.airline.entity.comparator.PlaneComparator;
import by.epam.buhai.airline.entity.comparator.SortingParameters;
import by.epam.buhai.airline.entity.validation.Validator;
import by.epam.buhai.airline.service.AirlineService;
import by.epam.buhai.airline.service.ServiceFactory;
import by.epam.buhai.airline.service.service_exception.PlaneListCreationFailedException;

import java.io.Serializable;
import java.util.*;

public final class AirlineManager implements Serializable {
    private static final long serialVersionUID = -4454739363445255356L;
    private ServiceFactory factory = ServiceFactory.getInstance();
    private AirlineService service = factory.getAirlineService();

    private static AirlineManager manager;
    private Airline airline;
    private static final Airline DEFAULT_AIRLINE = new Airline("Nameless");

    private AirlineManager(Airline airline) {
        this.airline = airline;
    }

    public static AirlineManager getAirlineManagerFor(Airline airline) {
        if (manager == null) {
            Optional<Airline> optional = Optional.ofNullable(airline);
            return new AirlineManager(optional.orElse(DEFAULT_AIRLINE));
        }
        return manager;
    }


    public void buyPlanes() throws PlaneListCreationFailedException {
        airline.setPlanes(service.createPlaneList());
    }


    public void addPlane(Plane plane) {
        airline.addPlane(plane);
    }


    public void removePlane(Plane plane) {
        airline.removePlane(plane);
    }


    public String getInfoAboutAirline() {
        return airline.toString();
    }


    public List<Plane> getPlanes() {
        return Collections.unmodifiableList(airline.getPlanes());
    }


    public int countTotalPassengerCapacity() {
        int result = 0;
        List<Plane> planes = airline.getPlanes();

        for (Plane p : planes) {
            if (p instanceof PassengerPlane) {
                result += ((PassengerPlane) p).getSeatingCapacity();
            }
        }
        return result;
    }


    public int countTotalCargoCapacity() {
        int result = 0;
        List<Plane> planes = airline.getPlanes();

        for (Plane p : planes) {
            if (p instanceof Freighter) {
                result += ((Freighter) p).getCargoTones();
            }
        }
        return result;
    }


    public List<Plane> sort(SortingParameters parameters) {
        List<Plane> planes = airline.getPlanes();

        planes.sort(PlaneComparator.getPlaneComparator(parameters));
        return Collections.unmodifiableList(planes);
    }


    public List<Plane> findPlaneByFuelConsumptionDiapason(int startLitersPerHour, int endLitersPerHour) {
        List<Plane> planes = airline.getPlanes();
        List<Plane> planesByFuelConsumption = new ArrayList<>();

        if (Validator.checkDiapasonInvalid(startLitersPerHour, endLitersPerHour)) {
            for (Plane p : planes) {
                int fuel = p.getFuelConsumptionLitersPerHour();

                if ((fuel >= startLitersPerHour)
                        && (fuel <= endLitersPerHour)) {
                    planesByFuelConsumption.add(p);
                }
            }
        }
        return Collections.unmodifiableList(planesByFuelConsumption);
    }
}
