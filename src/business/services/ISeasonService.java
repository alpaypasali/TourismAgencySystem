package business.services;

import core.utilities.results.SuccessInformationResult;
import entity.Season;

import java.util.ArrayList;

public interface ISeasonService {

    SuccessInformationResult create(Season season);
    SuccessInformationResult update(Season season);
    SuccessInformationResult delete(int id);
    Season getById(int id);
    ArrayList<Season> getAll();
    ArrayList<Object[]> getForTable(int size);
}
