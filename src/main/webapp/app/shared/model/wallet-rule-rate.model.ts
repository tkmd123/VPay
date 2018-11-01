import { IWalletRule } from 'app/shared/model//wallet-rule.model';

export interface IWalletRuleRate {
  id?: number;
  walletRuleRateCode?: string;
  walletRuleRateName?: string;
  walletRuleRateDesc?: string;
  walletRuleRateFromValue?: number;
  walletRuleRateToValue?: number;
  walletRuleRateDiscount?: number;
  walletRule?: IWalletRule;
}

export const defaultValue: Readonly<IWalletRuleRate> = {};
