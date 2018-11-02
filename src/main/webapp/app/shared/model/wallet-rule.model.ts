import { Moment } from 'moment';
import { IProductType } from 'app/shared/model//product-type.model';
import { IPayPartner } from 'app/shared/model//pay-partner.model';
import { IWalletRuleRate } from 'app/shared/model//wallet-rule-rate.model';

export interface IWalletRule {
  id?: number;
  walletRuleCode?: string;
  walletRuleName?: string;
  walletRuleDesc?: string;
  walletRuleFromDate?: Moment;
  walletRuleToDate?: Moment;
  walletRuleUDF1?: string;
  walletRuleUDF2?: string;
  walletRuleUDF3?: string;
  productType?: IProductType;
  payPartner?: IPayPartner;
  walletRules?: IWalletRuleRate[];
}

export const defaultValue: Readonly<IWalletRule> = {};
