import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IWalletRule } from 'app/shared/model/wallet-rule.model';
import { getEntities as getWalletRules } from 'app/entities/wallet-rule/wallet-rule.reducer';
import { getEntity, updateEntity, createEntity, reset } from './wallet-rule-rate.reducer';
import { IWalletRuleRate } from 'app/shared/model/wallet-rule-rate.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IWalletRuleRateUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IWalletRuleRateUpdateState {
  isNew: boolean;
  walletRuleId: string;
}

export class WalletRuleRateUpdate extends React.Component<IWalletRuleRateUpdateProps, IWalletRuleRateUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      walletRuleId: '0',
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

    this.props.getWalletRules();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { walletRuleRateEntity } = this.props;
      const entity = {
        ...walletRuleRateEntity,
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
    this.props.history.push('/entity/wallet-rule-rate');
  };

  render() {
    const { walletRuleRateEntity, walletRules, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="vPayApp.walletRuleRate.home.createOrEditLabel">Create or edit a WalletRuleRate</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : walletRuleRateEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="wallet-rule-rate-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="walletRuleRateCodeLabel" for="walletRuleRateCode">
                    Wallet Rule Rate Code
                  </Label>
                  <AvField id="wallet-rule-rate-walletRuleRateCode" type="text" name="walletRuleRateCode" />
                </AvGroup>
                <AvGroup>
                  <Label id="walletRuleRateNameLabel" for="walletRuleRateName">
                    Wallet Rule Rate Name
                  </Label>
                  <AvField id="wallet-rule-rate-walletRuleRateName" type="text" name="walletRuleRateName" />
                </AvGroup>
                <AvGroup>
                  <Label id="walletRuleRateDescLabel" for="walletRuleRateDesc">
                    Wallet Rule Rate Desc
                  </Label>
                  <AvField id="wallet-rule-rate-walletRuleRateDesc" type="text" name="walletRuleRateDesc" />
                </AvGroup>
                <AvGroup>
                  <Label id="walletRuleRateFromValueLabel" for="walletRuleRateFromValue">
                    Wallet Rule Rate From Value
                  </Label>
                  <AvField
                    id="wallet-rule-rate-walletRuleRateFromValue"
                    type="string"
                    className="form-control"
                    name="walletRuleRateFromValue"
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="walletRuleRateToValueLabel" for="walletRuleRateToValue">
                    Wallet Rule Rate To Value
                  </Label>
                  <AvField
                    id="wallet-rule-rate-walletRuleRateToValue"
                    type="string"
                    className="form-control"
                    name="walletRuleRateToValue"
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="walletRuleRateDiscountLabel" for="walletRuleRateDiscount">
                    Wallet Rule Rate Discount
                  </Label>
                  <AvField
                    id="wallet-rule-rate-walletRuleRateDiscount"
                    type="string"
                    className="form-control"
                    name="walletRuleRateDiscount"
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="walletRule.id">Wallet Rule</Label>
                  <AvInput id="wallet-rule-rate-walletRule" type="select" className="form-control" name="walletRule.id">
                    <option value="" key="0" />
                    {walletRules
                      ? walletRules.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/wallet-rule-rate" replace color="info">
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
  walletRules: storeState.walletRule.entities,
  walletRuleRateEntity: storeState.walletRuleRate.entity,
  loading: storeState.walletRuleRate.loading,
  updating: storeState.walletRuleRate.updating,
  updateSuccess: storeState.walletRuleRate.updateSuccess
});

const mapDispatchToProps = {
  getWalletRules,
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
)(WalletRuleRateUpdate);
