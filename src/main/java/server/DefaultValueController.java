package server;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pojos.Category;
import pojos.DefaultValue;
import services.DefaultValueService;
import services.SessionService;

import java.util.ArrayList;

@RestController
public class DefaultValueController {
    private DefaultValueService dvs = new DefaultValueService();
    private SessionService ss = new SessionService();

    /**
     * Gets the all defaultvalues by category.
     * @param category The category of the activity
     * @return An arraylist of all the defaultvalues
     */
    @PostMapping("/get_descriptions_by_category")
    public ArrayList<DefaultValue> getDefaultValuesFor(@RequestBody Category category) {
        return dvs.getValuesFromCategory(category);

    }

    /**
     * Deletes a specified defaultvalue.
     * @param desc The description of the activity
     * @param sessionId The sessionId of the user
     */
    @DeleteMapping("/delete_dv/{sessionId}")
    public void deleteDefaultValue(@RequestBody String desc,
                                   @PathVariable("sessionId") String sessionId) {
        if (ss.sessionExists(sessionId)) {
            dvs.deleteDefaultValue(desc);
        }
    }

    /**
     * Gets a default value matching the given description.
     * @param desc The description of the activity
     * @return the default value that was requested
     */
    @PostMapping("/get_consumption_by_description")
    public DefaultValue getDefaultValue(@RequestBody String desc) {
        return dvs.getDefaultValue(desc);
    }

    /**
     * Adds the defaultvalue provided.
     * @param dv The defaultValue to be added
     * @param sessionId The sessionId belonging to the user
     * @return true if adding was successful and false if adding was unsuccessful
     */
    @PostMapping("/add_dv/{sessionId}")
    public boolean addDefaultValue(@RequestBody DefaultValue dv,
                                   @PathVariable("sessionId") String sessionId) {
        if (ss.sessionExists(sessionId)) {
            return dvs.createDefaultValue(dv);
        }

        return false;
    }

    public void setDvs(DefaultValueService dvs) {
        this.dvs = dvs;
    }

    public void setSs(SessionService ss) {
        this.ss = ss;
    }
}
