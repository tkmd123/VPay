import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPartner } from 'app/shared/model/partner.model';
import { getEntities as getPartners } from 'app/entities/partner/partner.reducer';
import { IWallet } from 'app/shared/model/wallet.model';
import { getEntities as getWallets } from 'app/entities/wallet/wallet.reducer';
import { getEntity, updateEntity, createEntity, reset } from './wallet-allotment.reducer';
import { IWalletAllotment } from 'app/shared/model/wallet-allotment.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IWalletAllotmentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IWalletAllotmentUpdateState {
  isNew: boolean;
  partnerId: string;
  walletId: string;
}

export class WalletAllotmentUpdate extends React.Component<IWalletAllotmentUpdateProps, IWalletAllotmentUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      partnerId: '0',
      walletId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getPartners();
    this.props.getWallets();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { walletAllotmentEntity } = this.props;
      const entity = {
        ...walletAllotmentEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/wallet-allotment');
  };

  render() {
    const { walletAllotmentEntity, partners, wallets, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="vPayApp.walletAllotment.home.createOrEditLabel">Create or edit a WalletAllotment</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : walletAllotmentEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="wallet-allotment-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="walletAllotmentAmountLabel" for="walletAllotmentAmount">
                    Wallet Allotment Amount
                  </Label>
                  <AvField
                    id="wallet-allotment-walletAllotmentAmount"
                    type="string"
                    className="form-control"
                    name="walletAllotmentAmount"
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="partner.id">Partner</Label>
                  <AvInput id="wallet-allotment-partner" type="select" className="form-control" name="partner.id">
                    <option value="" key="0" />
                    {partners
                      ? partners.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="wallet.id">Wallet</Label>
                  <AvInput id="wallet-allotment-wallet" type="select" className="form-control" name="wallet.id">
                    <option value="" key="0" />
                    {wallets
                      ? wallets.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/wallet-allotment" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  partners: storeState.partner.entities,
  wallets: storeState.wallet.entities,
  walletAllotmentEntity: storeState.walletAllotment.entity,
  loading: storeState.walletAllotment.loading,
  updating: storeState.walletAllotment.updating,
  updateSuccess: storeState.walletAllotment.updateSuccess
});

const mapDispatchToProps = {
  getPartners,
  getWallets,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(WalletAllotmentUpdate);
