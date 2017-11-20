package galenscovell.sandbox.states;

import galenscovell.sandbox.entities.components.StateComponent;


public enum CropAgent implements State<StateComponent> {
    SEED() {
        @Override
        public void enter(StateComponent stateComponent) {
            System.out.println(String.format("Crop: Enter %s", getName()));
        }

        @Override
        public void exit(StateComponent stateComponent) {
            System.out.println(String.format("Crop: Exit %s", getName()));
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
            System.out.println(String.format("Crop: Enter %s", getName()));
        }

        @Override
        public void exit(StateComponent stateComponent) {
            System.out.println(String.format("Crop: Exit %s", getName()));
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
            System.out.println(String.format("Crop: Enter %s", getName()));
        }

        @Override
        public void exit(StateComponent stateComponent) {
            System.out.println(String.format("Crop: Exit %s", getName()));
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
            System.out.println(String.format("Crop: Enter %s", getName()));
        }

        @Override
        public void exit(StateComponent stateComponent) {
            System.out.println(String.format("Crop: Exit %s", getName()));
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
            System.out.println(String.format("Crop: Enter %s", getName()));
        }

        @Override
        public void exit(StateComponent stateComponent) {
            System.out.println(String.format("Crop: Exit %s", getName()));
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
