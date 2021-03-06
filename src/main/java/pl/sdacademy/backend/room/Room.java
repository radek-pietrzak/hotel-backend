package pl.sdacademy.backend.room;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @NotEmpty
    @Column(unique = true)
    private String roomNr;
    @NotNull
    private boolean isCleaned;
    @NotNull
    private int numPeople;  // np "3-person room"
    @NotNull
    private BigDecimal price;  // a separate Entity would be better - so that price can depend on Date
    // could be more but that's not the point I think


    public Room() {
    }

    public Room(String roomNr,boolean isCleaned, int numPeople, BigDecimal price) {
        this.roomNr = roomNr;
        this.isCleaned = isCleaned;
        this.numPeople = numPeople;
        this.price = price;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getRoomNr() {
        return roomNr;
    }

    public void setRoomNr(String roomNr) {
        this.roomNr = roomNr;
    }

    public boolean isCleaned() {
        return isCleaned;
    }

    public void setCleaned(boolean cleaned) {
        isCleaned = cleaned;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return isCleaned == room.isCleaned && numPeople == room.numPeople && Objects.equals(Id, room.Id) && Objects.equals(roomNr, room.roomNr) && Objects.equals(price, room.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, roomNr, isCleaned, numPeople, price);
    }

    @Override
    public String toString() {
        return "Room{" +
                "Id=" + Id +
                ", roomNr='" + roomNr + '\'' +
                ", isCleaned=" + isCleaned +
                ", numPeople=" + numPeople +
                ", price=" + price +
                '}';
    }
}
