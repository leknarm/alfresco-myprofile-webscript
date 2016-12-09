{
  "userName": "${userName}",
  "groups": [
  <#list containerGroups as group>
    "${group.properties.authorityName}" <#if group_has_next>,</#if>
  </#list>
  ]
}