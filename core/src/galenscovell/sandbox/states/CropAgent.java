package galenscovell.sandbox.states;

import galenscovell.sandbox.entities.components.StateComponent;


public enum CropAgent implements State<StateComponent> {
    SEED() {
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
            return "seed";
        }
    },
    SPROUT() {
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
            return "sprout";
        }
    },
    IMMATURE() {
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
            return "immature";
        }
    },
    MATURE() {
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
            return "mature";
        }
    },
    HARVEST() {
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
            return "harvest";
        }
    }
}
