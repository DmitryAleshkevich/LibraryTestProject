package libraryprojectmodel;

import javax.persistence.*;

/**
 * Created by aldm on 10.02.2016.
 */
@Entity
public class Card {
    private int id;

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int number;

    @Basic
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Card() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;

        Card card = (Card) o;

        return getId() == card.getId();

    }

    @Override
    public int hashCode() {
        return getId();
    }

}
