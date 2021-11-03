

module "ctags" {
  source      = "git::https://github.com/hmcts/terraform-module-common-tags.git?ref=master"
  environment = var.environment
  product     = var.prefix
  builtFrom   = var.builtFrom
}
locals {
  common_tags = module.ctags.common_tags
}