import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './wallet-transaction-type.reducer';
import { IWalletTransactionType } from 'app/shared/model/wallet-transaction-type.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IWalletTransactionTypeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IWalletTransactionTypeUpdateState {
  isNew: boolean;
}

export class WalletTransactionTypeUpdate extends React.Component<IWalletTransactionTypeUpdateProps, IWalletTransactionTypeUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { walletTransactionTypeEntity } = this.props;
      const entity = {
        ...walletTransactionTypeEntity,
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
    this.props.history.push('/entity/wallet-transaction-type');
  };

  render() {
    const { walletTransactionTypeEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="vPayApp.walletTransactionType.home.createOrEditLabel">Create or edit a WalletTransactionType</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : walletTransactionTypeEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="wallet-transaction-type-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="walletTransTypeCodeLabel" for="walletTransTypeCode">
                    Wallet Trans Type Code
                  </Label>
                  <AvField
                    id="wallet-transaction-type-walletTransTypeCode"
                    type="text"
                    name="walletTransTypeCode"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="walletTransTypeNameLabel" for="walletTransTypeName">
                    Wallet Trans Type Name
                  </Label>
                  <AvField
                    id="wallet-transaction-type-walletTransTypeName"
                    type="text"
                    name="walletTransTypeName"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="walletTransTypeDescLabel" for="walletTransTypeDesc">
                    Wallet Trans Type Desc
                  </Label>
                  <AvField id="wallet-transaction-type-walletTransTypeDesc" type="text" name="walletTransTypeDesc" />
                </AvGroup>
                <AvGroup>
                  <Label id="walletTransTypeFlagLabel" for="walletTransTypeFlag">
                    Wallet Trans Type Flag
                  </Label>
                  <AvField
                    id="wallet-transaction-type-walletTransTypeFlag"
                    type="string"
                    className="form-control"
                    name="walletTransTypeFlag"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="walletTransTypeUDF1Label" for="walletTransTypeUDF1">
                    Wallet Trans Type UDF 1
                  </Label>
                  <AvField id="wallet-transaction-type-walletTransTypeUDF1" type="text" name="walletTransTypeUDF1" />
                </AvGroup>
                <AvGroup>
                  <Label id="walletTransTypeUDF2Label" for="walletTransTypeUDF2">
                    Wallet Trans Type UDF 2
                  </Label>
                  <AvField id="wallet-transaction-type-walletTransTypeUDF2" type="text" name="walletTransTypeUDF2" />
                </AvGroup>
                <AvGroup>
                  <Label id="walletTransTypeUDF3Label" for="walletTransTypeUDF3">
                    Wallet Trans Type UDF 3
                  </Label>
                  <AvField id="wallet-transaction-type-walletTransTypeUDF3" type="text" name="walletTransTypeUDF3" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/wallet-transaction-type" replace color="info">
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
  walletTransactionTypeEntity: storeState.walletTransactionType.entity,
  loading: storeState.walletTransactionType.loading,
  updating: storeState.walletTransactionType.updating,
  updateSuccess: storeState.walletTransactionType.updateSuccess
});

const mapDispatchToProps = {
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
)(WalletTransactionTypeUpdate);
