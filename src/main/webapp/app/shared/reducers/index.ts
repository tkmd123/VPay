import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
import sessions, { SessionsState } from 'app/modules/account/sessions/sessions.reducer';
// prettier-ignore
import walletType, {
  WalletTypeState
} from 'app/entities/wallet-type/wallet-type.reducer';
// prettier-ignore
import partner, {
  PartnerState
} from 'app/entities/partner/partner.reducer';
// prettier-ignore
import payPartner, {
  PayPartnerState
} from 'app/entities/pay-partner/pay-partner.reducer';
// prettier-ignore
import walletTransctionType, {
  WalletTransctionTypeState
} from 'app/entities/wallet-transction-type/wallet-transction-type.reducer';
// prettier-ignore
import wallet, {
  WalletState
} from 'app/entities/wallet/wallet.reducer';
// prettier-ignore
import walletRule, {
  WalletRuleState
} from 'app/entities/wallet-rule/wallet-rule.reducer';
// prettier-ignore
import walletRuleRate, {
  WalletRuleRateState
} from 'app/entities/wallet-rule-rate/wallet-rule-rate.reducer';
// prettier-ignore
import walletAllotment, {
  WalletAllotmentState
} from 'app/entities/wallet-allotment/wallet-allotment.reducer';
// prettier-ignore
import walletTransaction, {
  WalletTransactionState
} from 'app/entities/wallet-transaction/wallet-transaction.reducer';
// prettier-ignore
import payPartnerLog, {
  PayPartnerLogState
} from 'app/entities/pay-partner-log/pay-partner-log.reducer';
// prettier-ignore
import partnerLog, {
  PartnerLogState
} from 'app/entities/partner-log/partner-log.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly sessions: SessionsState;
  readonly walletType: WalletTypeState;
  readonly partner: PartnerState;
  readonly payPartner: PayPartnerState;
  readonly walletTransctionType: WalletTransctionTypeState;
  readonly wallet: WalletState;
  readonly walletRule: WalletRuleState;
  readonly walletRuleRate: WalletRuleRateState;
  readonly walletAllotment: WalletAllotmentState;
  readonly walletTransaction: WalletTransactionState;
  readonly payPartnerLog: PayPartnerLogState;
  readonly partnerLog: PartnerLogState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  sessions,
  walletType,
  partner,
  payPartner,
  walletTransctionType,
  wallet,
  walletRule,
  walletRuleRate,
  walletAllotment,
  walletTransaction,
  payPartnerLog,
  partnerLog,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
