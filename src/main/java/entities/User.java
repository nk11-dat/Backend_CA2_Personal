package entities;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "users")
@NamedQuery(name = "User.deleteAllRows", query = "DELETE from User")
public class User implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_name", length = 25)
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "user_pass")
    private String userPass;
    @JoinTable(name = "user_roles", joinColumns = {
            @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
            @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
    @ManyToMany
    private List<Role> roleList = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_dogs",
            joinColumns = @JoinColumn(name = "users_user_name"),
            inverseJoinColumns = @JoinColumn(name = "dogs_id"))
    private Set<Dog> dogs = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "user_cats",
            joinColumns = @JoinColumn(name = "users_user_name"),
            inverseJoinColumns = @JoinColumn(name = "cats_id"))
    private Set<Cat> cats = new LinkedHashSet<>();

    public User() {
    }

    public User(String userName, String userPass) {
        this.userName = userName;
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
    }

    public List<String> getRolesAsStrings() {
        if (roleList.isEmpty()) {
            return null;
        }
        List<String> rolesAsStrings = new ArrayList<>();
        roleList.forEach((role) -> {
            rolesAsStrings.add(role.getRoleName());
        });
        return rolesAsStrings;
    }

    public boolean verifyPassword(String pw) { //TODO Change when password is hashed
        return BCrypt.checkpw(pw, this.userPass);
    }

    public void addDog(Dog dog) {
        this.dogs.add(dog);
    }

    public void removeDog(Dog dog) {
        this.dogs.remove(dog);
    }

    public void addCat(Cat cat) {
        this.cats.add(cat);
    }

    public void removeCat(Cat cat) {
        this.cats.remove(cat);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return this.userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public void addRole(Role userRole) {
        roleList.add(userRole);
    }

    public Set<Cat> getCats() {
        return cats;
    }

    public void setCats(Set<Cat> cats) {
        this.cats = cats;
    }

    public Set<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(Set<Dog> dogs) {
        this.dogs = dogs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUserName().equals(user.getUserName()) && getUserPass().equals(user.getUserPass()) && Objects.equals(getRoleList(), user.getRoleList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserName(), getUserPass(), getRoleList());
    }
}
