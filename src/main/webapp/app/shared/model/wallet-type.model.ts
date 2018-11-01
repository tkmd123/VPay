import { IWallet } from 'app/shared/model//wallet.model';
import { IWalletRule } from 'app/shared/model//wallet-rule.model';

export interface IWalletType {
  id?: number;
  walletTypeCode?: string;
  walletTypeName?: string;
  walletTypeDesc?: string;
  walletTypes?: IWallet[];
  walletTypes?: IWalletRule[];
}

export const defaultValue: Readonly<IWalletType> = {};
