package business.services;

import core.utilities.results.SuccessInformationResult;
import entity.PensionType;

import java.util.ArrayList;

public interface IPensionTypeService {
    SuccessInformationResult create(PensionType pensionType);
    SuccessInformationResult update(PensionType pensionType);
    SuccessInformationResult delete(int id);
    PensionType getById(int id);
    ArrayList<PensionType> getAll();
    ArrayList<Object[]> getForTable(int size);
}
