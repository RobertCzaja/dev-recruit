package Model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;

public class JustJoinItOffer {

    private final String slug;
    private final String title;
    private final ArrayList<String> requiredSkills;
    private final String experienceLevel;
    private final Instant publishedAt;
    private final String companyName;
    private final ArrayList<Salary> salaries;
    private final String workplaceType;

    public JustJoinItOffer(
            String slug,
            String title,
            ArrayList<String> requiredSkills,
            String experienceLevel,
            Instant publishedAt,
            String companyName,
            ArrayList<Salary> salaries,
            String workplaceType
    ) {
        this.slug = slug;
        this.title = title;
        this.requiredSkills = requiredSkills;
        this.experienceLevel = experienceLevel;
        this.publishedAt = publishedAt;
        this.companyName = companyName;
        this.salaries = salaries;
        this.workplaceType = workplaceType;
    }

    public String getSlug() {
        return slug;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getRequiredSkills() {
        return requiredSkills;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public String getCompanyName() {
        return companyName;
    }

    public ArrayList<Salary> getSalaries() {
        return salaries;
    }

    public String getWorkplaceType() {
        return workplaceType;
    }
}
