package titi.quiz.vmware.domain;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "user_services",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "service_id")})
    private Set<Service> services = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getId() {
        return id;
    }

    /*public User(String name, int age, Service... services) {
        this.setName(name);
        this.setAge(age);
        this.services = Stream.of(services).collect(Collectors.toSet());
        this.services.forEach(x->x.getUsers().add(this));
    }*/

    public User(String name, int age) {
        this.setName(name);
        this.setAge(age);
    }

    public Set<Service> getServices() {
        return services;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }

    public void addService(Service s) {
        if(!services.contains(s)) services.add(s);
        if(!s.getUsers().contains(this)) {
            s.getUsers().add(this);
        }
    }

    public void removeService(Service s) {
        if(!services.contains(s)) return;
        services.remove(s);
        if(!s.getUsers().contains(this)) return;
        s.getUsers().remove(this);
    }

    public User() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
