package business.concrete;

import business.services.IPensionTypeService;
import core.utilities.exceptions.BusinessException;
import core.utilities.exceptions.DatabaseException;
import core.utilities.handlers.ErrorHandler;
import core.utilities.results.SuccessInformationResult;
import dao.Abstract.IPensionTypeDal;
import dao.concrete.PensionTypeDal;
import entity.PensionType;
import entity.User;

import java.util.ArrayList;

public class PensionTypeManager implements IPensionTypeService {

    private IPensionTypeDal pensionTypeDal;

    public PensionTypeManager() {
        this.pensionTypeDal = new PensionTypeDal();
    }
    @Override
    public SuccessInformationResult create(PensionType pensionType) {
        try {

            PensionTypeCannotBeEmpty(pensionType);
            boolean createdPensionType = pensionTypeDal.create(pensionType);
            if(createdPensionType == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationResult successMessage = new SuccessInformationResult("Created PensionType successful.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public SuccessInformationResult update(PensionType pensionType) {
        try {

            PensionTypeCannotBeEmpty(pensionType);
            boolean createdPensionType = pensionTypeDal.update(pensionType);
            if(createdPensionType == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationResult successMessage = new SuccessInformationResult("Updated PensionType successful.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public SuccessInformationResult delete(int id) {
        try {

            PensionTypeCannotBeEmpty(id);
            boolean deletedPensionType = pensionTypeDal.delete(id);
            if(deletedPensionType == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationResult successMessage = new SuccessInformationResult("Deleted PensionType successfully.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public PensionType getById(int id) {
        try{
            PensionType pensionType = pensionTypeDal.getById(id);
            this.PensionTypeCannotBeEmpty(pensionType);
            return  pensionType;

        }catch (Exception e){
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public ArrayList<PensionType> getAll() {
        ArrayList<PensionType> pensionTypes = pensionTypeDal.getAll();
        if(pensionTypes.isEmpty())
            return  null;

        return pensionTypes;
    }

    @Override
    public ArrayList<Object[]> getForTable(int size) {
        ArrayList<Object[]> arrayList = new ArrayList<>();

        for (PensionType u : this.getAll()) {
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = u.getId();
            rowObject[i++] = u.getName();
            arrayList.add(rowObject);
        }
        return arrayList;
    }

    private  void PensionTypeCannotBeEmpty(PensionType pensionType) throws BusinessException {

        if (pensionType == null) {
            throw new BusinessException("PensionType cannot find");
        }

    }
    private  void PensionTypeCannotBeEmpty(int id) throws BusinessException {
        PensionType pensionType = pensionTypeDal.getById(id);
        if (pensionType == null) {
            throw new BusinessException("PensionType cannot find");
        }

    }
}
