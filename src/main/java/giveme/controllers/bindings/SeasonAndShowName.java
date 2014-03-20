package giveme.controllers.bindings;

import giveme.common.beans.Season;

/**
	 * The class used for the new season form
	 * @author couty
	 *
	 */
	public class SeasonAndShowName
	{
		private Season season;
		private String showName;
		
		public SeasonAndShowName(){
			
		}

		public Season getSeason() {
			return season;
		}

		public void setSeason(Season season) {
			this.season = season;
		}

		public String getShowName() {
			return showName;
		}

		public void setShowName(String showName) {
			this.showName = showName;
		}
	}