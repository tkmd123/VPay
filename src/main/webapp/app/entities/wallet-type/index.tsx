import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import WalletType from './wallet-type';
import WalletTypeDetail from './wallet-type-detail';
import WalletTypeUpdate from './wallet-type-update';
import WalletTypeDeleteDialog from './wallet-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WalletTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WalletTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WalletTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={WalletType} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={WalletTypeDeleteDialog} />
  </>
);

export default Routes;
