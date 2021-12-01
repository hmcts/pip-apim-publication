locals {
  root_path           = "./infrastructure/template"
  api_operations_json = jsondecode(file("${local.root_path}/policies.json"))
  api_operations = flatten([for v in local.api_operations_json.policies : {
    "operation_id" = v.operationId,
    "xml_content"  = file("${local.root_path}/${v.templateFile}")
  }])
  env       = (var.env == "aat") ? "stg" : (var.env == "sandbox") ? "sbox" : "${(var.env == "perftest") ? "test" : "${var.env}"}"
  apim_name = "sds-api-mgmt-${local.env}"
  apim_rg   = "ss-${local.env}-network-rg"

  api_name = "${var.product}-publication-service-api"
}

data "azurerm_api_management_product" "apim_product" {
  product_id          = "${var.product}-product-${local.env}"
  resource_group_name = local.apim_rg
  api_management_name = local.apim_name
}

module "apim_api" {
  source = "git@github.com:hmcts/cnp-module-api-mgmt-api?ref=master"

  api_mgmt_name  = local.apim_name
  api_mgmt_rg    = local.apim_rg
  display_name   = local.api_name
  name           = local.api_name
  path           = var.product
  product_id     = ""#data.azurerm_api_management_product.apim_product.product_id
  protocols      = ["https"]
  revision       = "1"
  service_url    = var.service_url
  swagger_url    = var.open_api_spec_content_value
  content_format = var.open_api_spec_content_format
}

module "apim_api_policy" {
  source                 = "git@github.com:hmcts/cnp-module-api-mgmt-api-policy?ref=master"
  api_mgmt_name          = local.apim_name
  api_mgmt_rg            = local.apim_rg
  api_name               = local.api_name
  api_policy_xml_content = file("${local.root_path}/api-policy.xml")
}

resource "azurerm_api_management_api_operation_policy" "apim_api_operation_policy" {
  for_each      = { for operation in local.api_operations : operation.operation_id => operation }
  operation_id        = each.value.operation_id
  api_name            = local.api_name
  api_management_name = local.apim_name
  resource_group_name = local.apim_rg
  xml_content         = each.value.xml_content
}
