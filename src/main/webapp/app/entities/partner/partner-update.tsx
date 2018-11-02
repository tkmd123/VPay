import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './partner.reducer';
import { IPartner } from 'app/shared/model/partner.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPartnerUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPartnerUpdateState {
  isNew: boolean;
}

export class PartnerUpdate extends React.Component<IPartnerUpdateProps, IPartnerUpdateState> {
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
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { partnerEntity } = this.props;
      const entity = {
        ...partnerEntity,
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
    this.props.history.push('/entity/partner');
  };

  render() {
    const { partnerEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="vPayApp.partner.home.createOrEditLabel">Create or edit a Partner</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : partnerEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="partner-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="partnerCodeLabel" for="partnerCode">
                    Partner Code
                  </Label>
                  <AvField
                    id="partner-partnerCode"
                    type="text"
                    name="partnerCode"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="partnerNameLabel" for="partnerName">
                    Partner Name
                  </Label>
                  <AvField
                    id="partner-partnerName"
                    type="text"
                    name="partnerName"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="partnerDescLabel" for="partnerDesc">
                    Partner Desc
                  </Label>
                  <AvField id="partner-partnerDesc" type="text" name="partnerDesc" />
                </AvGroup>
                <AvGroup>
                  <Label id="partnerOrderLabel" for="partnerOrder">
                    Partner Order
                  </Label>
                  <AvField
                    id="partner-partnerOrder"
                    type="string"
                    className="form-control"
                    name="partnerOrder"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="partnerUDF1Label" for="partnerUDF1">
                    Partner UDF 1
                  </Label>
                  <AvField id="partner-partnerUDF1" type="text" name="partnerUDF1" />
                </AvGroup>
                <AvGroup>
                  <Label id="partnerUDF2Label" for="partnerUDF2">
                    Partner UDF 2
                  </Label>
                  <AvField id="partner-partnerUDF2" type="text" name="partnerUDF2" />
                </AvGroup>
                <AvGroup>
                  <Label id="partnerUDF3Label" for="partnerUDF3">
                    Partner UDF 3
                  </Label>
                  <AvField id="partner-partnerUDF3" type="text" name="partnerUDF3" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/partner" replace color="info">
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
  partnerEntity: storeState.partner.entity,
  loading: storeState.partner.loading,
  updating: storeState.partner.updating,
  updateSuccess: storeState.partner.updateSuccess
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
)(PartnerUpdate);
