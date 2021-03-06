package by.epam.buhai.airline.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import java.io.Serializable;
import static by.epam.buhai.airline.entity.Specification.*;

public class CommercialSpaceplane extends PassengerPlane implements Serializable {
    private static final long serialVersionUID = 1834480623456207263L;
    private SpaceplaneTypes spaceplaneType;
    private int ticketPriceDollars;
    private int minutesInMicrogravity;

    public CommercialSpaceplane() {
    }

    public CommercialSpaceplane(String name, Manufacturers manufacturer, int crew,
                                int maxSpeedKmPerHour, int rangeKm, int fuelConsumptionLitersPerHour,
                                int seatingCapacity, SpaceplaneTypes spaceplaneType, int ticketPriceDollars,
                                int minutesInMicrogravity) {

        super(PlaneTypes.COMMERCIAL_SPACEPLANE, name, manufacturer, crew, maxSpeedKmPerHour, rangeKm,
                fuelConsumptionLitersPerHour, seatingCapacity);
        this.spaceplaneType = spaceplaneType;
        this.ticketPriceDollars = ticketPriceDollars;
        this.minutesInMicrogravity = minutesInMicrogravity;
    }

    public SpaceplaneTypes getSpaceplaneType() {
        return spaceplaneType;
    }

    public void setSpaceplaneType(SpaceplaneTypes spaceplaneType) {
        this.spaceplaneType = spaceplaneType;
    }

    public int getTicketPriceDollars() {
        return ticketPriceDollars;
    }

    public void setTicketPriceDollars(int ticketPriceDollars) {
        this.ticketPriceDollars = ticketPriceDollars;
    }


    public int getMinutesInMicrogravity() {
        return minutesInMicrogravity;
    }

    public void setMinutesInMicrogravity(int minutesInMicrogravity) {
        this.minutesInMicrogravity = minutesInMicrogravity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        if (this == o) return true;

        CommercialSpaceplane that = (CommercialSpaceplane) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(getTicketPriceDollars(), that.getTicketPriceDollars())
                .append(getMinutesInMicrogravity(), that.getMinutesInMicrogravity())
                .append(getSpaceplaneType(), that.getSpaceplaneType())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(getSpaceplaneType())
                .append(getTicketPriceDollars())
                .append(getMinutesInMicrogravity())
                .toHashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(super.toString());
        sb.append(", spaceplaneType=").append(spaceplaneType);
        sb.append(", minutesInMicrogravity=").append(minutesInMicrogravity);
        sb.append(", ticketPriceDollars=").append(ticketPriceDollars);
        sb.append(';');
        return sb.toString();
    }
}
