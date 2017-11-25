package galenscovell.sandbox.states;

import galenscovell.sandbox.entities.components.StateComponent;


public enum CropAgent implements State<StateComponent> {
    STAGE0() {
        @Override
        public void enter(StateComponent stateComponent) {

        }

        @Override
        public void exit(StateComponent stateComponent) {

        }

        @Override
        public void update(float delta, StateComponent stateComponent) {

        }

        @Override
        public String getName() {
            return "stage0";
        }

        @Override
        public int getId() {
            return 0;
        }
    },
    STAGE1() {
        @Override
        public void enter(StateComponent stateComponent) {

        }

        @Override
        public void exit(StateComponent stateComponent) {

        }

        @Override
        public void update(float delta, StateComponent stateComponent) {

        }

        @Override
        public String getName() {
            return "stage1";
        }

        @Override
        public int getId() {
            return 1;
        }
    },
    STAGE2() {
        @Override
        public void enter(StateComponent stateComponent) {

        }

        @Override
        public void exit(StateComponent stateComponent) {

        }

        @Override
        public void update(float delta, StateComponent stateComponent) {

        }

        @Override
        public String getName() {
            return "stage2";
        }

        @Override
        public int getId() {
            return 2;
        }
    },
    STAGE3() {
        @Override
        public void enter(StateComponent stateComponent) {

        }

        @Override
        public void exit(StateComponent stateComponent) {

        }

        @Override
        public void update(float delta, StateComponent stateComponent) {

        }

        @Override
        public String getName() {
            return "stage3";
        }

        @Override
        public int getId() {
            return 3;
        }
    },
    STAGE4() {
        @Override
        public void enter(StateComponent stateComponent) {

        }

        @Override
        public void exit(StateComponent stateComponent) {

        }

        @Override
        public void update(float delta, StateComponent stateComponent) {

        }

        @Override
        public String getName() {
            return "stage4";
        }

        @Override
        public int getId() {
            return 4;
        }
    },
    STAGE5() {
        @Override
        public void enter(StateComponent stateComponent) {

        }

        @Override
        public void exit(StateComponent stateComponent) {

        }

        @Override
        public void update(float delta, StateComponent stateComponent) {

        }

        @Override
        public String getName() {
            return "stage5";
        }

        @Override
        public int getId() {
            return 5;
        }
    }
}
