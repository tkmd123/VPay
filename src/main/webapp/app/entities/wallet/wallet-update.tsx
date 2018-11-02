import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProductType } from 'app/shared/model/product-type.model';
import { getEntities as getProductTypes } from 'app/entities/product-type/product-type.reducer';
import { IPartner } from 'app/shared/model/partner.model';
import { getEntities as getPartners } from 'app/entities/partner/partner.reducer';
import { IPayPartner } from 'app/shared/model/pay-partner.model';
import { getEntities as getPayPartners } from 'app/entities/pay-partner/pay-partner.reducer';
import { getEntity, updateEntity, createEntity, reset } from './wallet.reducer';
import { IWallet } from 'app/shared/model/wallet.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IWalletUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IWalletUpdateState {
  isNew: boolean;
  productTypeId: string;
  partnerId: string;
  payPartnerId: string;
}

export class WalletUpdate extends React.Component<IWalletUpdateProps, IWalletUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      productTypeId: '0',
      partnerId: '0',
      payPartnerId: '0',
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

    this.props.getProductTypes();
    this.props.getPartners();
    this.props.getPayPartners();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { walletEntity } = this.props;
      const entity = {
        ...walletEntity,
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
    this.props.history.push('/entity/wallet');
  };

  render() {
    const { walletEntity, productTypes, partners, payPartners, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="vPayApp.wallet.home.createOrEditLabel">Create or edit a Wallet</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : walletEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="wallet-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="walletNumberLabel" for="walletNumber">
                    Wallet Number
                  </Label>
                  <AvField
                    id="wallet-walletNumber"
                    type="text"
                    name="walletNumber"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="walletIsActiveLabel" check>
                    <AvInput id="wallet-walletIsActive" type="checkbox" className="form-control" name="walletIsActive" />
                    Wallet Is Active
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="walletDescLabel" for="walletDesc">
                    Wallet Desc
                  </Label>
                  <AvField id="wallet-walletDesc" type="text" name="walletDesc" />
                </AvGroup>
                <AvGroup>
                  <Label id="walletUDF1Label" for="walletUDF1">
                    Wallet UDF 1
                  </Label>
                  <AvField id="wallet-walletUDF1" type="text" name="walletUDF1" />
                </AvGroup>
                <AvGroup>
                  <Label id="walletUDF2Label" for="walletUDF2">
                    Wallet UDF 2
                  </Label>
                  <AvField id="wallet-walletUDF2" type="text" name="walletUDF2" />
                </AvGroup>
                <AvGroup>
                  <Label id="walletUDF3Label" for="walletUDF3">
                    Wallet UDF 3
                  </Label>
                  <AvField id="wallet-walletUDF3" type="text" name="walletUDF3" />
                </AvGroup>
                <AvGroup>
                  <Label for="productType.id">Product Type</Label>
                  <AvInput id="wallet-productType" type="select" className="form-control" name="productType.id">
                    <option value="" key="0" />
                    {productTypes
                      ? productTypes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="partner.id">Partner</Label>
                  <AvInput id="wallet-partner" type="select" className="form-control" name="partner.id">
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
                  <Label for="payPartner.id">Pay Partner</Label>
                  <AvInput id="wallet-payPartner" type="select" className="form-control" name="payPartner.id">
                    <option value="" key="0" />
                    {payPartners
                      ? payPartners.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/wallet" replace color="info">
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
  productTypes: storeState.productType.entities,
  partners: storeState.partner.entities,
  payPartners: storeState.payPartner.entities,
  walletEntity: storeState.wallet.entity,
  loading: storeState.wallet.loading,
  updating: storeState.wallet.updating,
  updateSuccess: storeState.wallet.updateSuccess
});

const mapDispatchToProps = {
  getProductTypes,
  getPartners,
  getPayPartners,
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
)(WalletUpdate);
