package dtos;

import java.util.ArrayList;
import java.util.List;

public class CombinedJokeDTO
{
   String id;
   List<String> joke = new ArrayList<>();

    public CombinedJokeDTO(DadDTO dad, ChuckDTO chuck)
    {
        this.id = dad.getId() + " " + chuck.getId();
        this.joke.add( dad.getJoke());
        this.joke.add( chuck.getValue());
//        this.joke1 = chuck.getValue();
//        this.joke2 = dad.getJoke();
    }

    public String getId()
    {
        return id;
    }

    public List<String> getJoke()
    {
        return joke;
    }
}
