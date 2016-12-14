<import resource="classpath:alfresco/templates/webscripts/org/alfresco/repository/requestutils.lib.js">
<import resource="classpath:alfresco/templates/webscripts/org/alfresco/repository/generic-paged-results.lib.js">

function main()
{
  model.userName = person.properties.userName;
  model.firstName = person.properties.firstName;
  model.lastName = person.properties.lastName;
  model.jobtitle = person.properties.jobtitle;
  model.email = person.properties.email;
  model.containerGroups = people.getContainerGroups(person);
}
main();