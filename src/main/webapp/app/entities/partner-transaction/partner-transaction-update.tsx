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
import { IWalletTransaction } from 'app/shared/model/wallet-transaction.model';
import { getEntities as getWalletTransactions } from 'app/entities/wallet-transaction/wallet-transaction.reducer';
import { getEntity, updateEntity, createEntity, reset } from './partner-transaction.reducer';
import { IPartnerTransaction } from 'app/shared/model/partner-transaction.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPartnerTransactionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPartnerTransactionUpdateState {
  isNew: boolean;
  productTypeId: string;
  partnerId: string;
  walletTransactionId: string;
}

export class PartnerTransactionUpdate extends React.Component<IPartnerTransactionUpdateProps, IPartnerTransactionUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      productTypeId: '0',
      partnerId: '0',
      walletTransactionId: '0',
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
    this.props.getWalletTransactions();
  }

  saveEntity = (event, errors, values) => {
    values.partnerTransDate = new Date(values.partnerTransDate);

    if (errors.length === 0) {
      const { partnerTransactionEntity } = this.props;
      const entity = {
        ...partnerTransactionEntity,
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
    this.props.history.push('/entity/partner-transaction');
  };

  render() {
    const { partnerTransactionEntity, productTypes, partners, walletTransactions, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="vPayApp.partnerTransaction.home.createOrEditLabel">Create or edit a PartnerTransaction</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : partnerTransactionEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="partner-transaction-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="partnerTransAmountLabel" for="partnerTransAmount">
                    Partner Trans Amount
                  </Label>
                  <AvField
                    id="partner-transaction-partnerTransAmount"
                    type="string"
                    className="form-control"
                    name="partnerTransAmount"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="partnerTransDateLabel" for="partnerTransDate">
                    Partner Trans Date
                  </Label>
                  <AvInput
                    id="partner-transaction-partnerTransDate"
                    type="datetime-local"
                    className="form-control"
                    name="partnerTransDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.partnerTransactionEntity.partnerTransDate)}
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="partnerTransRefLabel" for="partnerTransRef">
                    Partner Trans Ref
                  </Label>
                  <AvField id="partner-transaction-partnerTransRef" type="text" name="partnerTransRef" />
                </AvGroup>
                <AvGroup>
                  <Label id="partnerTransUsernameLabel" for="partnerTransUsername">
                    Partner Trans Username
                  </Label>
                  <AvField id="partner-transaction-partnerTransUsername" type="text" name="partnerTransUsername" />
                </AvGroup>
                <AvGroup>
                  <Label id="partnerTransUDF1Label" for="partnerTransUDF1">
                    Partner Trans UDF 1
                  </Label>
                  <AvField id="partner-transaction-partnerTransUDF1" type="text" name="partnerTransUDF1" />
                </AvGroup>
                <AvGroup>
                  <Label id="partnerTransUDF2Label" for="partnerTransUDF2">
                    Partner Trans UDF 2
                  </Label>
                  <AvField id="partner-transaction-partnerTransUDF2" type="text" name="partnerTransUDF2" />
                </AvGroup>
                <AvGroup>
                  <Label id="partnerTransUDF3Label" for="partnerTransUDF3">
                    Partner Trans UDF 3
                  </Label>
                  <AvField id="partner-transaction-partnerTransUDF3" type="text" name="partnerTransUDF3" />
                </AvGroup>
                <AvGroup>
                  <Label for="productType.id">Product Type</Label>
                  <AvInput id="partner-transaction-productType" type="select" className="form-control" name="productType.id">
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
                  <AvInput id="partner-transaction-partner" type="select" className="form-control" name="partner.id">
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
                  <Label for="walletTransaction.id">Wallet Transaction</Label>
                  <AvInput id="partner-transaction-walletTransaction" type="select" className="form-control" name="walletTransaction.id">
                    <option value="" key="0" />
                    {walletTransactions
                      ? walletTransactions.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/partner-transaction" replace color="info">
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
  walletTransactions: storeState.walletTransaction.entities,
  partnerTransactionEntity: storeState.partnerTransaction.entity,
  loading: storeState.partnerTransaction.loading,
  updating: storeState.partnerTransaction.updating,
  updateSuccess: storeState.partnerTransaction.updateSuccess
});

const mapDispatchToProps = {
  getProductTypes,
  getPartners,
  getWalletTransactions,
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
)(PartnerTransactionUpdate);
