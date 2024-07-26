@extends("layouts.dash")
@section("content")

<div class="row">
                            <div class="col-12">
                                <div class="card">
                                    <div class="card-body">

                                        <h4 class="card-title">Textual inputs</h4>
                                        
                                        

                                        <form method="POST" action="{{ route('subbs') }}" >
                                            @csrf
                                            
                                            <div class="row mb-3">
                                            <label for="example-text-input" class="col-sm-2 col-form-label">Registration No</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="text" placeholder="Artisanal kale" id="regno" name="regno">
                                            </div>
                                        </div>

                                        <!-- end row -->
                                        <div class="row mb-3">
                                            <label for="example-search-input" class="col-sm-2 col-form-label">School Name</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="text" placeholder="How do I shoot web" id="schoolname" name="schoolname">
                                            </div>
                                        </div>
                                        <!-- end row -->
                                        <div class="row mb-3">
                                            <label for="example-email-input" class="col-sm-2 col-form-label">Email</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="email" placeholder="bootstrap@example.com" id="email" name="email">
                                            </div>
                                        </div>
                                        <!-- end row -->
                                        <div class="row mb-3">
                                            <label for="example-url-input" class="col-sm-2 col-form-label">District</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="text" placeholder="https://getbootstrap.com" id="district" name="district">
                                            </div>
                                        </div>
                                        <!-- end row -->
                                        <div class="row mb-3">
                                            <label for="example-tel-input" class="col-sm-2 col-form-label">Representative ID</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="tel" placeholder="1-(555)-555-5555" id="repid" name="repid">
                                            </div>
                                        </div>
                                        <div class="mb-0">
                                                <div>
                                                    <button type="submit" class="btn btn-primary waves-effect waves-light me-1">
                                                        Submit
                                                    </button>
                                                    <button type="reset" class="btn btn-secondary waves-effect">
                                                        Cancel
                                                    </button>
                                                </div>
                                            </div>
                                        <!-- end row -->
                                                </form>
                                    </div>
                                </div>
                            </div> <!-- end col -->
                        </div>
                        <!-- end row -->

                        
                           
                        
                        <!-- end row -->

                        
  
                                                            @endsection