package business.concrete;

import business.services.ISeasonService;
import core.utilities.exceptions.BusinessException;
import core.utilities.exceptions.DatabaseException;
import core.utilities.handlers.ErrorHandler;
import core.utilities.results.SuccessInformationResult;
import dao.Abstract.ISeasonsDal;
import dao.concrete.SeasonsDal;
import entity.PensionType;
import entity.RoomType;
import entity.Season;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SeasonManager implements ISeasonService {
    private ISeasonsDal seasonsDal;
    public SeasonManager(){
        seasonsDal = new SeasonsDal();
    }
    @Override
    public SuccessInformationResult create(Season season) {
        try {

            SeasonsCannotBeEmpty(season);
            boolean createdSeason= seasonsDal.create(season);
            if(createdSeason == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationResult successMessage = new SuccessInformationResult("Created Season successful.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public SuccessInformationResult update(Season season) {
        try {

            SeasonsCannotBeEmpty(season);
            boolean createdSeason= seasonsDal.update(season);
            if(createdSeason == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationResult successMessage = new SuccessInformationResult("Updated Season successful.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public SuccessInformationResult delete(int id) {
        try {

            SeasonsCannotBeEmpty(id);
            boolean deletedPensionType = seasonsDal.delete(id);
            if(deletedPensionType == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationResult successMessage = new SuccessInformationResult("Deleted Season successfully.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public Season getById(int id) {
        try{
            Season season = seasonsDal.getByid(id);
            this.SeasonsCannotBeEmpty(season);
            return  season;

        }catch (Exception e){
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public ArrayList<Season> getAll() {
        ArrayList<Season> seasons = seasonsDal.getAll();
        if(seasons.isEmpty())
            return  null;

        return seasons;
    }

    @Override
    public ArrayList<Object[]> getForTable(int size , ArrayList<Season> seasonArrayList) {
        ArrayList<Object[]> arrayList = new ArrayList<>();
        if (seasonArrayList == null) {
            return arrayList; // Return empty list if modelList is null
        }
        for (Season u : seasonArrayList) {
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = u.getSeason_id();
            rowObject[i++] = u.getHotel().getHotelName();
            rowObject[i++] = u.getStart_date();
            rowObject[i++] = u.getEnd_date();
            arrayList.add(rowObject);
        }
        return arrayList;
    }

    public boolean checkIfHasSeason(int hotelId) {
        List<Season> seasons = seasonsDal.getAll();
        return seasons.stream().anyMatch(season -> season.getHotel_id() == hotelId);
    }

    @Override
    public Season getByHotelId(int hotelId) {
        List<Season> seasons = seasonsDal.getAll();
        Optional<Season> seasonOptional = seasons.stream()
                .filter(season -> season.getHotel_id() == hotelId)
                .findFirst();
        return seasonOptional.orElse(null);
    }

    private  void SeasonsCannotBeEmpty(Season season) throws BusinessException {

        if (season == null) {
            throw new BusinessException("Season cannot find");
        }

    }
    private  void SeasonsCannotBeEmpty(int id) throws BusinessException {
        Season season = seasonsDal.getByid(id);
        if (season == null) {
            throw new BusinessException("Season cannot find");
        }


    }
}
