package dtos;

import java.util.ArrayList;
import java.util.List;

public class CombinedCatDTO
{
   String id;
   List<String> joke = new ArrayList<>();

    public CombinedCatDTO(CatFactDTO fact, CatImageDTO img)
    {

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
