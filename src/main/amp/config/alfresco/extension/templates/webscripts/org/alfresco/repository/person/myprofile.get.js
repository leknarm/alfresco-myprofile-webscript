<import resource="classpath:alfresco/templates/webscripts/org/alfresco/repository/requestutils.lib.js">
<import resource="classpath:alfresco/templates/webscripts/org/alfresco/repository/generic-paged-results.lib.js">

function main()
{
  model.userName = person.properties.userName;
  model.containerGroups = people.getContainerGroups(person);
}
main();