package fi.capeismi.fish.uistelupaivakirja.controller;

import java.util.Comparator;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import fi.capeismi.fish.uistelupaivakirja.model.DuplicateItemException;
import fi.capeismi.fish.uistelupaivakirja.model.EventItem;
import fi.capeismi.fish.uistelupaivakirja.model.LureObject;
import fi.capeismi.fish.uistelupaivakirja.model.ModelFactory;
import fi.capeismi.fish.uistelupaivakirja.model.TripObject;

public class FishCounterPart extends CounterPart {
	
	private Comparator<Object> m_comparator = null;
	
	private class AlternativeHandler implements OnDismissListener, OnClickListener{		
		private int m_source;
		public AlternativeHandler(int source)
		{
			m_source = source;
		}
		
		@Override
		public void onClick(View arg0) {
			AddSpinnerItem adder = new AddSpinnerItem(m_activity.getActivity());
			adder.setOnDismissListener(this);
			adder.show();			
		}

		@Override
		public void onDismiss(DialogInterface dialog) {
			try
			{
				switch(m_source)
				{
				case R.id.Species:
					ModelFactory.getModel().getSpinnerItems().addSpecies(dialog.toString());
					m_activity.setSpinners(R.id.Species, 
							ModelFactory.getModel().getSpinnerItems().getSpeciesList(),
							m_comparator);
					break;
				case R.id.Getter:
					ModelFactory.getModel().getSpinnerItems().addGetter(dialog.toString());
					m_activity.setSpinners(R.id.Getter, 
							ModelFactory.getModel().getSpinnerItems().getGetterList(),
							m_comparator);
					break;
				case R.id.Method:
					ModelFactory.getModel().getSpinnerItems().addMethod(dialog.toString());
					m_activity.setSpinners(R.id.Method, 
							ModelFactory.getModel().getSpinnerItems().getMethodList(),
							m_comparator);					
					break;
				}
				
				m_activity.setSpinnerDefaultValue(m_source, dialog.toString());
			}catch(DuplicateItemException e)
			{
				//No worry
			}
			
		}
		
	}
	
	public FishCounterPart(EventItem eventitem, TripObject trip, Event.PrivateConduit activity) {
		super(eventitem, trip, activity);
		setupFishFields();
		readFishFields();
	}

	
    protected void setupFishFields()
    {   	
		m_comparator = new Comparator<Object>() {
			@Override
			public int compare(Object arg0, Object arg1) {
				return arg0.toString().compareTo(arg1.toString());					
			}			
		};
		
		m_activity.setCheckboxListener(R.id.GroupCB, new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				m_activity.setEnabled(R.id.GroupSize, arg1);
				m_activity.setEnabled(R.id.Length, !arg1);
			}
    		
    	});
    
		m_activity.setButtonListener(R.id.AddSpecies, new AlternativeHandler(R.id.Species));
		m_activity.setButtonListener(R.id.AddGetter, new AlternativeHandler(R.id.Getter));
		m_activity.setButtonListener(R.id.AddMethod, new AlternativeHandler(R.id.Method));
    	m_activity.setSpinners(R.id.Species, 
    			ModelFactory.getModel().getSpinnerItems().getSpeciesList(),
    			m_comparator);
    	
    	m_activity.setSpinners(R.id.Getter, 
    			ModelFactory.getModel().getSpinnerItems().getGetterList(),
    			m_comparator);
    	
    	m_activity.setSpinners(R.id.Method, 
    			ModelFactory.getModel().getSpinnerItems().getMethodList(),
    			m_comparator);
    	
    	m_activity.setSpinners(R.id.Lure,
    			ModelFactory.getModel().getLures().getList(),
    			m_comparator);   
    	    	
    }
    
    public void readFishFields()
    {
    	m_activity.setEditText(R.id.Length, m_event.getLength());
    	m_activity.setEditText(R.id.Weight, m_event.getWeight());
    	m_activity.setEditText(R.id.SpotDepth, m_event.getSpotDepth());
    	m_activity.setEditText(R.id.Depth, m_event.getTotalDepth());
    	m_activity.setEditText(R.id.TrollingSpeed, m_event.getTrollingSpeed());
    	m_activity.setEditText(R.id.LureWeight, m_event.getLineWeight());
    	m_activity.setEditText(R.id.ReleaseWidth, m_event.getReleaseWidth());    	
    	m_activity.setEditText(R.id.GroupSize, m_event.getGroupAmount());
    	
    	m_activity.setSpinnerDefaultValue(R.id.Getter, m_event.getGetter());
    	m_activity.setSpinnerDefaultValue(R.id.Method, m_event.getMethod());
    	m_activity.setSpinnerDefaultValue(R.id.Species, m_event.getSpecies());
    	
    	m_activity.setChecked(R.id.GroupCB, m_event.getIsGroup());
    	m_activity.setChecked(R.id.CrCB, m_event.getIsCatchNReleased());
    	m_activity.setChecked(R.id.UnderSizeCB, m_event.getIsUndersize());
    	
    	if(m_event.getLure() != null)
    		m_activity.setSpinnerDefaultValue(R.id.Lure, m_event.getLure().toString());
    }
    
    public void writeFishFields()
    {
		m_event.setLength(m_activity.getEditText(R.id.Length));
		m_event.setWeight(m_activity.getEditText(R.id.Weight));
		m_event.setSpotDepth(m_activity.getEditText(R.id.SpotDepth));
		m_event.setTotalDepth(m_activity.getEditText(R.id.Depth));
		m_event.setTrollingSpeed(m_activity.getEditText(R.id.TrollingSpeed));
		m_event.setLineWeight(m_activity.getEditText(R.id.LureWeight));
		m_event.setReleaseWidth(m_activity.getEditText(R.id.ReleaseWidth));
		m_event.setGroupAmount(m_activity.getEditText(R.id.GroupSize));
    	m_event.setIsGroup(m_activity.getChecked(R.id.GroupCB));
    	m_event.setIsUndersize(m_activity.getChecked(R.id.UnderSizeCB));
    	m_event.setIsCatchNReleased(m_activity.getChecked(R.id.CrCB));    	    
		
		Object species = m_activity.getSelectedItem(R.id.Species);
		Object getter = m_activity.getSelectedItem(R.id.Getter);
		Object method = m_activity.getSelectedItem(R.id.Method);
		Object lure = m_activity.getSelectedItem(R.id.Lure);
		if(species != null)	m_event.setSpecies(species.toString());
		if(getter != null)	m_event.setGetter(getter.toString());
		if(method != null)	m_event.setMethod(method.toString());
		if(lure != null)	m_event.setLure((LureObject) lure);
    }
}
