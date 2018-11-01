import { Moment } from 'moment';
import { IWalletType } from 'app/shared/model//wallet-type.model';
import { IPayPartner } from 'app/shared/model//pay-partner.model';
import { IWalletRuleRate } from 'app/shared/model//wallet-rule-rate.model';

export interface IWalletRule {
  id?: number;
  walletRuleCode?: string;
  walletRuleName?: string;
  walletRuleDesc?: string;
  walletRuleFromDate?: Moment;
  walletRuleToDate?: Moment;
  walletType?: IWalletType;
  payPartner?: IPayPartner;
  walletRules?: IWalletRuleRate[];
}

export const defaultValue: Readonly<IWalletRule> = {};
