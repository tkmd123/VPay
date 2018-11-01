import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IWalletTransctionType } from 'app/shared/model/wallet-transction-type.model';
import { getEntities as getWalletTransctionTypes } from 'app/entities/wallet-transction-type/wallet-transction-type.reducer';
import { IWalletAllotment } from 'app/shared/model/wallet-allotment.model';
import { getEntities as getWalletAllotments } from 'app/entities/wallet-allotment/wallet-allotment.reducer';
import { getEntity, updateEntity, createEntity, reset } from './wallet-transaction.reducer';
import { IWalletTransaction } from 'app/shared/model/wallet-transaction.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IWalletTransactionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IWalletTransactionUpdateState {
  isNew: boolean;
  walletTransctionTypeId: string;
  walletAllotmentId: string;
}

export class WalletTransactionUpdate extends React.Component<IWalletTransactionUpdateProps, IWalletTransactionUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      walletTransctionTypeId: '0',
      walletAllotmentId: '0',
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

    this.props.getWalletTransctionTypes();
    this.props.getWalletAllotments();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { walletTransactionEntity } = this.props;
      const entity = {
        ...walletTransactionEntity,
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
    this.props.history.push('/entity/wallet-transaction');
  };

  render() {
    const { walletTransactionEntity, walletTransctionTypes, walletAllotments, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="vPayApp.walletTransaction.home.createOrEditLabel">Create or edit a WalletTransaction</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : walletTransactionEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="wallet-transaction-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label for="walletTransctionType.id">Wallet Transction Type</Label>
                  <AvInput
                    id="wallet-transaction-walletTransctionType"
                    type="select"
                    className="form-control"
                    name="walletTransctionType.id"
                  >
                    <option value="" key="0" />
                    {walletTransctionTypes
                      ? walletTransctionTypes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="walletAllotment.id">Wallet Allotment</Label>
                  <AvInput id="wallet-transaction-walletAllotment" type="select" className="form-control" name="walletAllotment.id">
                    <option value="" key="0" />
                    {walletAllotments
                      ? walletAllotments.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/wallet-transaction" replace color="info">
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
  walletTransctionTypes: storeState.walletTransctionType.entities,
  walletAllotments: storeState.walletAllotment.entities,
  walletTransactionEntity: storeState.walletTransaction.entity,
  loading: storeState.walletTransaction.loading,
  updating: storeState.walletTransaction.updating,
  updateSuccess: storeState.walletTransaction.updateSuccess
});

const mapDispatchToProps = {
  getWalletTransctionTypes,
  getWalletAllotments,
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
)(WalletTransactionUpdate);
