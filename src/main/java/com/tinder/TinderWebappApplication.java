package com.tinder;

import static com.tinder.model.Constants.FACEBOOK_TOKEN;
import static com.tinder.model.Constants.PAGE_MAIN;
import static com.tinder.model.Constants.PAGE_MATCH;
import static com.tinder.model.Constants.PAGE_MATCHES;
import static com.tinder.model.Constants.PAGE_RECS;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import com.tinder.model.TinderAPI;
import com.tinder.model.webservice.data.TinderUser;
import com.tinder.view.MainMenuView;
import com.tinder.view.MatchView;
import com.tinder.view.MatchesView;
import com.tinder.view.RecommendationsView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

@SpringBootApplication
// database is not yet configured, disabled until it is
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class TinderWebappApplication {

    public static final Logger LOGGER = Logger.getLogger(TinderWebappApplication.class.getSimpleName());

    public static void main(String[] args) {
        SpringApplication.run(TinderWebappApplication.class, args);
    }

    @SuppressWarnings("serial")
    @Theme("valo")
    @SpringUI(path = "")
    public static class VaadinUI extends UI {
        @Autowired
        private TinderAPI tinderAPI;

        @Autowired
        private MainMenuView mainMenuView;

        @Autowired
        private MatchesView matchesView;

        @Autowired
        private MatchView matchView;

        private VaadinUI() {}
        
        @Override
        protected void init(VaadinRequest request) {
            TinderUser tinderUser = tinderAPI.authorize(FACEBOOK_TOKEN);
            getPage().setTitle("Tinder Webapp");
            RecommendationsView recsView = new RecommendationsView(tinderUser.getToken(), getUI(), tinderAPI);

            // Create a navigator to control the views
            Navigator navigator = new Navigator(this, this);

            // Init necessary parameters
            mainMenuView.setNavigator(navigator);
            matchesView.setNavigator(navigator);
            matchesView.setUserToken(tinderUser.getToken());
            matchView.setUserToken(tinderUser.getToken());

            // Create and register the views
            navigator.addView(PAGE_MAIN, mainMenuView);
            navigator.addView(PAGE_RECS, recsView);
            navigator.addView(PAGE_MATCHES, matchesView);
            navigator.addView(PAGE_MATCH, matchView);
            navigator.navigateTo(PAGE_MAIN);
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + ((mainMenuView == null) ? 0 : mainMenuView.hashCode());
            result = prime * result + ((matchView == null) ? 0 : matchView.hashCode());
            result = prime * result + ((matchesView == null) ? 0 : matchesView.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (!super.equals(obj))
                return false;
            if (getClass() != obj.getClass())
                return false;
            VaadinUI other = (VaadinUI) obj;
            if (mainMenuView == null) {
                if (other.mainMenuView != null)
                    return false;
            } else if (!mainMenuView.equals(other.mainMenuView))
                return false;
            if (matchView == null) {
                if (other.matchView != null)
                    return false;
            } else if (!matchView.equals(other.matchView))
                return false;
            if (matchesView == null) {
                if (other.matchesView != null)
                    return false;
            } else if (!matchesView.equals(other.matchesView))
                return false;
            return true;
        }
    }
}