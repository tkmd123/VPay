import { IWalletType } from 'app/shared/model//wallet-type.model';
import { IPayPartner } from 'app/shared/model//pay-partner.model';
import { IWalletAllotment } from 'app/shared/model//wallet-allotment.model';

export interface IWallet {
  id?: number;
  walletNumber?: string;
  walletIsActive?: boolean;
  walletDesc?: string;
  walletType?: IWalletType;
  payPartner?: IPayPartner;
  wallets?: IWalletAllotment[];
}

export const defaultValue: Readonly<IWallet> = {
  walletIsActive: false
};
