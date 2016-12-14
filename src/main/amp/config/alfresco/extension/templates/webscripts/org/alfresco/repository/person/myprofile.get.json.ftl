{
  "userName": "${userName}",
  "firstName": "${firstName!}",
  "lastName": "${lastName!}",
  "jobtitle": "${jobtitle!}",
  "email": "${email!}",
  "groups": [
  <#list containerGroups as group>
    "${group.properties.authorityName}" <#if group_has_next>,</#if>
  </#list>
  ]
}