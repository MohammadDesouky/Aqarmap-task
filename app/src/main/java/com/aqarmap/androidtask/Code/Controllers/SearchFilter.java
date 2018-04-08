package com.aqarmap.androidtask.Code.Controllers;

import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.aqarmap.androidtask.AndroidTaskApp;
import com.aqarmap.androidtask.Code.Structures.Filter.LocationField;
import com.aqarmap.androidtask.Code.Structures.Filter.PropertyTypeField;
import com.aqarmap.androidtask.Code.Structures.Filter.SectionField;
import com.aqarmap.androidtask.Code.Structures.JSONs.JSONLocation;
import com.aqarmap.androidtask.Code.Structures.JSONs.JSONPrice;
import com.aqarmap.androidtask.Code.Structures.JSONs.JSONPropertyType;
import com.aqarmap.androidtask.Code.Structures.JSONs.JSONSection;
import com.aqarmap.androidtask.Code.Structures.JSONs.JSONTitledResult;
import com.aqarmap.androidtask.Code.Structures.Web.URLWithParams;
import com.aqarmap.androidtask.Code.Threading.IThreadTask;
import com.aqarmap.androidtask.Code.Utilities.JSON;
import com.aqarmap.androidtask.Code.Utilities.SharedPref;
import com.aqarmap.androidtask.Code.Utilities.Strings;
import com.aqarmap.androidtask.R;
import com.aqarmap.androidtask.UI.Listeners.SelectLocationBtnClickListner;
import com.aqarmap.androidtask.UI.Listeners.SelectPropertyTypeBtnClickListner;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Mohammad Desouky on 02/04/2018.
 */

public class SearchFilter
{

    //todo update this params names with actual param names
    //I supposed the webservice takes it's parmeters via url using GET
    //ue can use space the url builder will handle it in a proper way
    static final String HTTP_PARAM_PAGE_TO_LOAD = "Load Page",
            HTTP_PARAM_PROP_TYPE = "Property type",
            HTTP_PARAM_PROP_SECTION = "Section",
            HTTP_PARAM_PROP_LOCATION = "LOCATION",
            HTTP_PARAM_MIN_PRICE = "MIN",
            HTTP_PARAM_MAX_PRICE = "Max";
    final int ITEMS_TO_LOAD = 4;
    // Search Filter Fields
    SectionField mSectionField;
    LocationField mLocationField;
    PropertyTypeField mPropertyTypeField;
    ArrayAdapter<JSONSection> SectionsAdapter;
    ArrayAdapter<JSONPrice> MinPricesAdapter, MaxPricesAdapter;
    JSONSection SelectedSection, SavedSection;
    JSONPrice SelectedMinPrice, SavedMinPrice, SelectedMaxPrice, SavedMaxPrice;
    JSONLocation SelectedLocation, SavedLocation;
    JSONTitledResult SelectedType, SavedType;
    Activity OwnerActivity;
    Spinner SectionSpinner, MinSpinner, MaxSpinner;
    Button LocationsBtn, PropertyTypesBtn;
    /**
     * Trying to read data from URLs in the strings resource file
     *
     * @return true if data reached and read successfully, false otherwise
     */
    IThreadTask<Void, Void, Void> LoadingFinishedCallBack;
    boolean Loading = false;
    int LoadingCounter = 0;
    IThreadTask<Void, Void, JSONArray> BackgroundLoadingCounterCallback = new IThreadTask<Void, Void, JSONArray>()
    {
        @Override
        public void BeforeRun()
        {

        }

        @Override
        public JSONArray Action(Void... Params)
        {
            return null;
        }

        @Override
        public void AfterRun(JSONArray Result)
        {
            LoadingCounter++;
            if (ITEMS_TO_LOAD == LoadingCounter && LoadingFinishedCallBack != null)
            {
                //save to local and call the callback
                saveDataToSharedPref();
                Loading = false;
                LoadingFinishedCallBack.AfterRun(null);
            }
        }

        @Override
        public void OnProgress(Void[] Values)
        {

        }
    };
    AdapterView.OnItemSelectedListener SectionsOnItemSelected = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            //update if the selected value CHANGES
            JSONSection newSection = SectionsAdapter.getItem(position);
            if (!newSection.equals(SelectedSection))
            {
                SelectedSection = newSection;
                //update min and max adapters
                UpdateMinPrices();
                UpdateMaxPrices(false);
                MinSpinner.setSelection(0);
                MaxSpinner.setSelection(0);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {

        }
    },
            MinPriceOnItemSelected = new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    try
                    {
                        //update if the selected value CHANGES
                        final JSONPrice newMinPrice = MinPricesAdapter.getItem(position);
                        //if (!newMinPrice.equals(SelectedMinPrice))
                        {
                            SelectedMinPrice = newMinPrice;
                            final JSONPrice oldMax = (JSONPrice) MaxSpinner.getSelectedItem();
                            UpdateMaxPrices(false);
                            new Handler().postDelayed(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    int maxIdToSelect = oldMax.getValue() < newMinPrice.getValue() ? 1 :
                                            getMaxPositionInAdapter(oldMax);
                                    setSelection(MaxSpinner, maxIdToSelect);
                                    MaxPricesAdapter.notifyDataSetChanged();
                                }
                            }, 200);


                        }
                    } catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            },
            MaxPriceOnItemSelected = new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    try
                    {
                        //update if the selected value CHANGES
                        JSONPrice newMaxPrice = MaxPricesAdapter.getItem(position);
                        // if (!newMaxPrice.equals(SelectedMaxPrice))
                        {
                            SelectedMaxPrice = newMaxPrice;
                        }
                    } catch (Exception ex)
                    {
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            };

    public SearchFilter()
    {
        mSectionField = new SectionField();
        mLocationField = new LocationField();
        mPropertyTypeField = new PropertyTypeField();
    }

    public void setOwnerActivity(Activity ownerActivity)
    {
        OwnerActivity = ownerActivity;
        SectionsAdapter = new ArrayAdapter<>(OwnerActivity, android.R.layout.simple_list_item_1);
        MinPricesAdapter = new ArrayAdapter<>(OwnerActivity, android.R.layout.simple_list_item_1);
        MaxPricesAdapter = new ArrayAdapter<>(OwnerActivity, android.R.layout.simple_list_item_1);
    }

    public void UpdateLayoutComponents(Spinner Section, Spinner MinPrice, Spinner MaxPrice,
                                       Button PropertyType, Button Locations)
    {
        //assignment
        SectionSpinner = Section;
        MinSpinner = MinPrice;
        MaxSpinner = MaxPrice;
        PropertyTypesBtn = PropertyType;
        LocationsBtn = Locations;
        //load data
        SectionSpinner.setAdapter(getSectionAdapter());
        MinSpinner.setAdapter(getMinPricesAdapter());
        MaxSpinner.setAdapter(getMaxPricesAdapter());


        //update selections
        UpdateSpinnersValues();

        PropertyTypesBtn.setText(getSavedOrDefaultPropertyType());
        LocationsBtn.setText(getSavedOrDefaultLocation());

    }

    void SetEventListeners()
    {
        //events
        SectionSpinner.setOnItemSelectedListener(SectionsOnItemSelected);
        MinSpinner.setOnItemSelectedListener(MinPriceOnItemSelected);
        MaxSpinner.setOnItemSelectedListener(MaxPriceOnItemSelected);
        LocationsBtn.setOnClickListener(new SelectLocationBtnClickListner(OwnerActivity));
        PropertyTypesBtn.setOnClickListener(new SelectPropertyTypeBtnClickListner(OwnerActivity));
    }

    private void UpdateSpinnersValues()
    {
        new Handler().postDelayed(new Runnable()
        {

            @Override
            public void run()
            {

                //SectionSpinner.setSelection(getSavedOrDefaultSection(),false);
                setSelection(SectionSpinner, getSavedOrDefaultSection());
                SelectedSection = SectionsAdapter.getItem(getSavedOrDefaultSection());

                new Handler().postDelayed(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        UpdateMinPrices();
                        int min = getMinPositionInAdapter(getSavedOrDefaultMinPrice());
                        //MinSpinner.setSelection(min,false);
                        SelectedMinPrice = MinPricesAdapter.getItem(min);
                        setSelection(MinSpinner, min);

                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                UpdateMaxPrices(false);
                                int max = getMaxPositionInAdapter(getSavedOrDefaultMaxPrice());
                                //MaxSpinner.setSelection(max,false);
                                SelectedMaxPrice = MaxPricesAdapter.getItem(max == -1 ? 0 : max);
                                setSelection(MaxSpinner, max);

                                SetEventListeners();
                            }
                        }, 150);

                    }
                }, 150);
            }
        }, 150);
    }

    /**
     * Trying to read data from the shared preferences parse it and store in it's objects
     *
     * @return true if data exists and read successfully, false otherwise
     */
    public boolean LoadFromSharedPref()
    {
        if (SharedPref.DoIHaveValidFilterData())
        {
            JSONArray Section = JSON.getJSONArray(SharedPref.getStoredSections()),
                    Location = JSON.getJSONArray(SharedPref.getStoredLocations()),
                    Type = JSON.getJSONArray(SharedPref.getStoredPropTypes()),
                    Prices = JSON.getJSONArray(SharedPref.getStoredPrices());

            if (Section != null && Location != null && Type != null)
            {
                mSectionField = new SectionField(Section, Prices);
                mLocationField = new LocationField(Location);
                mPropertyTypeField = new PropertyTypeField(Type);
                return true;
            }
        }
        return false;
    }

    /**
     * Save data to shared preferences
     *
     * @return true if saved successfully, false otherwise
     */
    public boolean saveDataToSharedPref()
    {
        try
        {
            SharedPref.savePropertiesSections(mSectionField.getJSONArray());
            SharedPref.savePropertiesLocations(mLocationField.getJSONArray());
            SharedPref.savePropertiesTypes(mPropertyTypeField.getJSONArray());
            SharedPref.savePropertiesPrices(mSectionField.getPrices().getJSONArray());
            return true;
        } catch (Exception ex)
        {

        }
        return false;
    }

    public boolean saveSelectionChangesToSharedPref()
    {
        boolean ChangesSaved = false;
        if (SelectedType != null)
        {
            SharedPref.saveSelectedPropertyType(SelectedType);
            SavedType = SelectedType;
            ChangesSaved = true;
        }
        if (SelectedLocation != null)
        {
            SharedPref.saveSelectedLocation(SelectedLocation);
            SavedLocation = SelectedLocation;
            ChangesSaved = true;
        }
        if (SelectedSection != null)
        {
            SharedPref.saveSelectedSection(SelectedSection);
            SavedSection = SelectedSection;
            ChangesSaved = true;
        }
        if (SelectedMinPrice != null)
        {
            SharedPref.saveSeletedMinPrice(SelectedMinPrice);
            SavedMinPrice = SelectedMinPrice;
            ChangesSaved = true;
        }
        if (SelectedMaxPrice != null)
        {
            SharedPref.saveSelectedMaxPrice(SelectedMaxPrice);
            SavedMaxPrice = SelectedMaxPrice;
            ChangesSaved = true;
        }
        return ChangesSaved;
    }

    public boolean LoadFromWeb(IThreadTask<Void, Void, Void> Callback)
    {
        if (Loading) return false;
        Loading = true;
        try
        {
            LoadingFinishedCallBack = Callback;
            mSectionField = new SectionField();
            mLocationField = new LocationField();
            mPropertyTypeField = new PropertyTypeField();
            mSectionField.LoadFromWeb(BackgroundLoadingCounterCallback);
            mLocationField.LoadFromWeb(BackgroundLoadingCounterCallback);
            mPropertyTypeField.LoadFromWeb(BackgroundLoadingCounterCallback);
            return true;
        } catch (Exception ex)
        {

        }
        return true;
    }

    SpinnerAdapter getSectionAdapter()
    {
        SectionsAdapter.clear();
        SectionsAdapter.addAll(mSectionField.Options());
        SectionsAdapter.notifyDataSetChanged();
        return SectionsAdapter;
    }

    SpinnerAdapter getMinPricesAdapter()
    {
        UpdateMinPrices();
        return MinPricesAdapter;
    }

    SpinnerAdapter getMaxPricesAdapter()
    {
        UpdateMaxPrices(false);
        return MaxPricesAdapter;
    }

    void UpdateMaxPrices(boolean NotifiyChanges)
    {
        MaxPricesAdapter.clear();
        mSectionField.UpdateSelectedSection(SelectedSection);
        MaxPricesAdapter.addAll(mSectionField.getMaxPrices(SelectedMinPrice));
        if (NotifiyChanges)
            MaxPricesAdapter.notifyDataSetChanged();
    }

    void UpdateMinPrices()
    {
        MinPricesAdapter.clear();
        mSectionField.UpdateSelectedSection(SelectedSection);
        MinPricesAdapter.addAll(mSectionField.getMinPrices());
        MinPricesAdapter.notifyDataSetChanged();
    }

    @Override
    public String toString()
    {
        try
        {
            String r = SavedType.GetTitle() + "(s) " + SavedSection.GetTitle() + " at " + SavedLocation.GetTitle();
            String price = "";

            if (SavedMinPrice.getValue() > 0)
                price = " Price at least " + Strings.toMoneyFormat((double) SavedMinPrice.getValue());
            if (SavedMaxPrice.getValue() > 0)
                price = " Price at most " + Strings.toMoneyFormat((double) SavedMaxPrice.getValue());
            if (SavedMinPrice.getValue() > 0 && SavedMaxPrice.getValue() > 0)
                price = " Price between " + Strings.toMoneyFormat((double) SavedMinPrice.getValue()) + " and " +
                        Strings.toMoneyFormat((double) SavedMaxPrice.getValue());
            return r.trim() + (!TextUtils.isEmpty(price) ? " " : "") + price.trim();
        } catch (Exception ex)
        {

        }
        return "Results";
    }

    public int getSavedOrDefaultSection()
    {
        JSONSection saved = new JSONSection(JSON.getObject(SharedPref.getStoredSelectedSection()));
        if (!TextUtils.isEmpty(saved.GetTitle()))
        {
            SavedSection = saved;
        } else
            //select the first in sections
            SavedSection = mSectionField.getDefault();
        SelectedSection = SavedSection;
        return getSectionPositionInAdapter(SelectedSection);
    }

    public String getSavedOrDefaultLocation()
    {
        JSONLocation saved = new JSONLocation(JSON.getObject(SharedPref.getStoredSelectedLocation()));
        if (!TextUtils.isEmpty(saved.GetTitle()))
        {
            SavedLocation = saved;
        } else
            //select the first in sections
            SavedLocation = mLocationField.getDefault();
        SelectedLocation = SavedLocation;
        return SavedLocation != null ? SavedLocation.GetTitle() : "";
    }

    public String getSavedOrDefaultPropertyType()
    {
        JSONPropertyType saved = new JSONPropertyType(JSON.getObject(SharedPref.getStoredSelectedPropType()));
        if (!TextUtils.isEmpty(saved.GetTitle()))
        {
            SavedType = saved;
        } else
            //select the first in sections
            SavedType = mPropertyTypeField.getDefault();
        SelectedType = SavedType;
        return SavedType != null ? SavedType.GetTitle() : "";
    }

    public JSONPrice getSavedOrDefaultMaxPrice()
    {
        JSONPrice saved = new JSONPrice(JSON.getObject(SharedPref.getStoredMaxPrice()));
        if (saved.getValue() > -1)
            SavedMaxPrice = saved;
        else
            //select the first
            SavedMaxPrice = mSectionField.getDefaultPrice();
        SelectedMaxPrice = SavedMaxPrice;
        return SavedMaxPrice;
    }

    public JSONPrice getSavedOrDefaultMinPrice()
    {
        JSONPrice saved = new JSONPrice(JSON.getObject(SharedPref.getStoredMinPrice()));
        if (saved.getValue() > -1)
            SavedMinPrice = saved;
        else
            //select the first
            SavedMinPrice = mSectionField.getDefaultPrice();
        SelectedMinPrice = SavedMinPrice;
        return SavedMinPrice;
    }

    public void UpdateSelectedValues()
    {
        getSavedOrDefaultLocation();
        getSavedOrDefaultMaxPrice();
        getSavedOrDefaultMinPrice();
        getSavedOrDefaultPropertyType();
        getSavedOrDefaultSection();
    }

    //todo handle paramenters as webservice required I send the id for location,property type and section
    public URLWithParams getSearchURL(String PageToLoad)
    {
        //update saved values
        UpdateSelectedValues();
        URLWithParams mURL = new URLWithParams(AndroidTaskApp.getAppContext().getResources().getString(R.string.search_base_url));
        mURL.AddParam(HTTP_PARAM_PAGE_TO_LOAD, PageToLoad);
        if (SavedLocation != null)
            mURL.AddParam(HTTP_PARAM_PROP_LOCATION, SavedLocation.GetID() + "");

        if (SavedSection != null)
            mURL.AddParam(HTTP_PARAM_PROP_SECTION, SavedSection.GetID() + "");

        if (SavedType != null)
            mURL.AddParam(HTTP_PARAM_PROP_TYPE, SavedType.GetID() + "");

        if (SavedMinPrice != null && SavedMinPrice.getValue() > 0)
            mURL.AddParam(HTTP_PARAM_MIN_PRICE, SavedMinPrice.getValue() + "");

        if (SavedMaxPrice != null && SavedMaxPrice.getValue() > 0)
            mURL.AddParam(HTTP_PARAM_MAX_PRICE, SavedMaxPrice.getValue() + "");

        return mURL;
    }

    public boolean HasChanges()
    {
        boolean section = SelectedSection.GetID() != SavedSection.GetID(),
                location = SelectedLocation.GetID() != SavedLocation.GetID(),
                type = SelectedType.GetID() != SavedType.GetID(),
                min = SelectedMinPrice.getValue() != SavedMinPrice.getValue(),
                max = SelectedMaxPrice.getValue() != SavedMaxPrice.getValue();
        return section || location || type || min || max;
    }

    private int getMinPositionInAdapter(JSONPrice element)
    {
        for (int i = 0; i < MinPricesAdapter.getCount(); i++)
            if (MinPricesAdapter.getItem(i).getValue() == element.getValue())
                return i;
        return -1;
    }

    private int getMaxPositionInAdapter(JSONPrice element)
    {
        for (int i = 0; i < MaxPricesAdapter.getCount(); i++)
            if (MaxPricesAdapter.getItem(i).getValue() == element.getValue())
                return i;
        return -1;
    }

    private int getSectionPositionInAdapter(JSONSection element)
    {
        if (SectionsAdapter != null)
            for (int i = 0; i < SectionsAdapter.getCount(); i++)
                if (SectionsAdapter.getItem(i).GetID() == element.GetID())
                    return i;
        return -1;
    }

    //spinner set selection problem
    private void setSelection(Spinner mSpinner, int index)
    {
        final Spinner s = mSpinner;
        final int i = index;
        s.post(new Runnable()
        {
            @Override
            public void run()
            {
                s.setSelection(i, false);
            }
        });
    }

    public ArrayList<JSONLocation> getLocations()
    {
        ArrayList<JSONLocation> ls = new ArrayList<>();
        if (mLocationField.getLoadedOptions() != null)
            ls.addAll(mLocationField.getLoadedOptions());
        return ls;
    }

    public ArrayList<JSONTitledResult> getPropertyTypes()
    {
        ArrayList<JSONTitledResult> ls = new ArrayList<>();
        if (mPropertyTypeField.getLoadedOptions() != null)
            ls.addAll(mPropertyTypeField.getLoadedOptions());
        return ls;
    }

    public void setSelectedLocation(JSONLocation selectedLocation)
    {
        SelectedLocation = selectedLocation;
        if (LocationsBtn != null)
        {
            LocationsBtn.setText(selectedLocation.GetTitle());
        }
    }

    public void setSelectedPropertyType(JSONTitledResult selectedPropertyType)
    {
        SelectedType = selectedPropertyType;
        if (PropertyTypesBtn != null)
        {
            PropertyTypesBtn.setText(SelectedType.GetTitle());
        }
    }

}
