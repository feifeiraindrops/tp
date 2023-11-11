---
  layout: default.md
  title: "Edison Siow's Project Portfolio Page"
---

### Project: ModuLight

ModuLight is a lightweight application for professors to manage and 
perform statistical analysis on the educational records of students. 
Given below are my contributions to the project.

* **New Features**:
  * `addGradedComponent`: allows users to add a graded component into the app.

  * `editGradedComponent`: allows users to edit a graded component in the app.

  * `deleteGradedComponent`: allows users to delete a graded component in the app.

* **Code contributed:**:
  * Major highlights: 
    * Modified the UI and JavaFX code to accommodate three entities 
    and dynamically update the UI whenever changes are made.
    * Designed the class infrastructure to link Student, GradedComponent,StudentScore
    and calculate mean scores.
  * For more details, refer to: https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=seraphimstreets&breakdown=false&sort=groupTitle%20dsc&sortWithin=title&since=2023-09-22&timeframe=commit&mergegroup=&groupSelect=groupByRepos


* **Project management**:
  * As group leader, delegated tasks and managed issue tracker. 
  

### Contributions to User Guide
### Graded Component Parameters

| Parameter | Description                                               | Constraints                                                                    | Valid Examples      | Invalid Examples             |
|-----------|-----------------------------------------------------------|--------------------------------------------------------------------------------|---------------------|------------------------------|
| c/        | Name of the graded component                              | Must only contain alphanumeric characters and must not be empty                | Midterm Exam, CA2   | 高考, CA2/Oral, Practical-Exam |
| w/        | Weightage of the graded component                         | Must be a non-negative number less than or equal to 100. Decimals are allowed. | 0, 0.25, 20         | -0.3, 1/2, (20), 1000.8, NIL |
| mm/       | Maximum marks for the graded component, in absolute terms | Must be a non-negative number less than or equal to 10000.                     | 0.0, 28, 100, 200.0 | -0.3, 1/2, (20), NIL         |

**Notes on Graded Component and Student Score parameters for score calculation**<br>

* The maximum marks of a graded component and marks of a student score are both absolute values and are used together to
  determine the relative performance of a student for a component. For instance, if the maximum marks for a component Midterms is 50, and the marks for the student is 35, then the student scored 35/50 =70% on this graded component.

* The weightage of a graded component is used to determine its contribution to a student’s overall score, and is calculated
  relative to the sum of all other component weightages. For instance, if there are only 2 components in the system currently,
  and component A has weightage 30, and component B weightage 20, then component A currently represents 20/(20+30) = 60% of
  the student’s overall score. This is modified as components are added and removed. Note that the total weightage of all graded components should be less than or equal to 100.

* If a graded component has a maximum mark of 0, the relative score for any associated student scores will be 0.

### Add a graded component: `addComp`
Adds a graded component to the database. The graded component name (case-sensitve) cannot match any other existing graded component names in the database. If successful, an acknowledgement message will be shown in the output box and data is saved. Otherwise, a failure message is shown instead specifying the cause of failure.

Upon successful creation of a graded component, a corresponding student score will be created for each student in the database. For instance, if a graded component with name “Midterms” is created and there are two students with student numbers “A1234567X” and “A1234567Y” in the database, then two student scores are created with titles  “A1234567X - Midterm” and “A1234567Y - Midterm”.

Weightage represents how much this component contributes when tabulating students’ total marks, and is calculated relative to the sum of all other component weightages. For instance, if there are only 2 components in the system currently, and component A has weightage 30, and component B weightage 20, then component A currently represents 60% of the grade. This is modified as components are added and removed.

Format: `addComp c/COMP_NAME w/WEIGHTAGE mm/MAX_MARKS`

Examples: `addComp c/Midterm w/30 mm/70`  adds a graded component called “Midterm” with a weightage of 30 and a maximum mark of 70.

### Edit a graded component: `editComp`
Edits an existing graded component’s details in the database, based on the 1-based index of the graded component shown in the Graded Components list. If successful, an acknowledgement message will be shown in the output box and data is saved. Otherwise, a failure message is shown instead specifying the cause of failure.
1 or more fields to be edited must be provided in the command. The index provided must be more than 0 and not exceed the number of graded components displayed in the Graded Components list. If the component name is being edited, the component name cannot match the component name of any other graded component already in the database.

Format: `editComp INDEX [c/COMP_NAME] [w/WEIGHTAGE] [mm/MAX_MARKS]`

Examples: `editComp 4 c/Midterm Exam mm/55` edits the fourth graded component in the Graded Components list to have a name of “Midterm Exam”, and a maximum mark of 55.

### Delete a graded component: `deleteComp`

Deletes an existing graded component in the database, based on the 1-based index of the graded component shown in the Graded Components list.
If successful, an acknowledgement message will be shown in the output box and data is saved. Otherwise, a failure message is shown instead specifying the cause of failure.

The index provided must be more than 0 and not exceed the number of graded components displayed in the Graded Components list.

Format: `deleteComp INDEX`

Examples: `deleteComp 2` deletes the second graded component in the displayed Graded Components List

### Contributions to Developer Guide
