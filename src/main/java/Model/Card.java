package Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by aldm on 10.02.2016.
 */
@Entity
public class Card {

    private int id;
    private int number;
    private Credential credential;

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @OneToOne(fetch = FetchType.LAZY)
    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
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
